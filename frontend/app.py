"""Streamlit frontend for the goal tracker API.

Talks to the Spring Boot backend over HTTP. The backend address comes from the
API_URL environment variable, so the same code runs locally, in Docker and on
a hosting platform without changes.
"""

import os

import requests
import streamlit as st

API_URL = os.getenv("API_URL", "http://localhost:8080").rstrip("/")
# Generous default: free hosting tiers can take a while to wake a sleeping backend.
TIMEOUT = float(os.getenv("API_TIMEOUT", "60"))
PERIODS = ["hour", "day", "week", "month", "year"]

st.set_page_config(page_title="Goal Tracker", page_icon="🎯")


class ApiError(Exception):
    """Raised when the backend can't be reached or rejects a request."""


def call_api(method: str, path: str, **kwargs):
    """Send one request to the backend and return its parsed JSON (or None)."""
    url = f"{API_URL}{path}"
    try:
        response = requests.request(method, url, timeout=TIMEOUT, **kwargs)
    except requests.exceptions.RequestException as exc:
        raise ApiError(
            f"Could not reach the API at {API_URL} ({exc.__class__.__name__})."
        ) from exc

    if response.status_code == 400:
        raise ApiError(
            "The API rejected that input (400). Check that every field is "
            "filled in and the numbers are above zero."
        )
    if not response.ok:
        raise ApiError(f"The API returned an error: HTTP {response.status_code}.")
    return response.json() if response.content else None


def list_goals() -> list[dict]:
    return call_api("GET", "/goals") or []


def create_goal(payload: dict):
    return call_api("POST", "/goals", json=payload)


def log_progress(goal_id: int):
    return call_api("PATCH", f"/goals/{goal_id}")


def delete_goal(goal_id: int):
    return call_api("DELETE", f"/goals/{goal_id}")


def flash(message: str) -> None:
    """Remember a message across the st.rerun() that follows an action."""
    st.session_state["flash"] = message


# ---------------------------------------------------------------- sidebar
with st.sidebar:
    st.header("Settings")
    st.caption(f"API: {API_URL}")
    if st.button("🔄 Refresh"):
        st.rerun()

# ------------------------------------------------------------------- page
st.title("🎯 Goal Tracker")

if "flash" in st.session_state:
    st.success(st.session_state.pop("flash"))

with st.expander("➕ Add a goal"):
    with st.form("new_goal", clear_on_submit=True):
        goal = st.text_input("Goal", placeholder="Become fluent in German")
        what_to_do = st.text_input(
            "What will you do?", placeholder="Finish Duolingo practice modules"
        )
        col_quantity, col_unit = st.columns(2)
        quantity = col_quantity.number_input("Quantity", min_value=1, value=1, step=1)
        unit = col_unit.text_input("Unit", placeholder="lesson")
        col_times, col_period = st.columns(2)
        num_times = col_times.number_input("How many times", min_value=1, value=1, step=1)
        period = col_period.selectbox("Per", PERIODS, index=1)
        submitted = st.form_submit_button("Add goal")

    if submitted:
        if not goal.strip() or not unit.strip():
            st.warning("Please fill in the goal and the unit.")
        else:
            try:
                create_goal(
                    {
                        "goal": goal.strip(),
                        "whatToDo": what_to_do.strip(),
                        "unit": unit.strip(),
                        "quantity": int(quantity),
                        "numTimes": int(num_times),
                        "period": period,
                    }
                )
            except ApiError as exc:
                st.error(str(exc))
            else:
                flash("Goal added!")
                st.rerun()

st.subheader("Your goals")

try:
    with st.spinner("Loading goals (the API may be waking up)..."):
        goals = list_goals()
except ApiError as exc:
    st.error(str(exc))
    st.stop()

if not goals:
    st.info("No goals yet. Add one above!")

for item in goals:
    goal_id = item["id"]
    count = item.get("count") or 0
    target = item.get("numTimes") or 1
    reached = count >= target

    with st.container(border=True):
        st.markdown(f"**{item['goal']}**")
        if item.get("whatToDo"):
            st.caption(item["whatToDo"])

        label = (
            f"{count} of {target} done per {item['period']} "
            f"({item['quantity']} {item['unit']} each)"
        )
        st.progress(min(count / target, 1.0), text=("🎉 " if reached else "") + label)

        col_done, col_delete, _ = st.columns([1, 1, 3])
        if col_done.button("+1 done", key=f"inc-{goal_id}"):
            try:
                log_progress(goal_id)
            except ApiError as exc:
                st.error(str(exc))
            else:
                st.rerun()
        if col_delete.button("Delete", key=f"del-{goal_id}"):
            try:
                delete_goal(goal_id)
            except ApiError as exc:
                st.error(str(exc))
            else:
                flash("Goal deleted.")
                st.rerun()
