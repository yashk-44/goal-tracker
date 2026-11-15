FROM python:3.12-slim

WORKDIR /app

# Install dependencies first so Docker caches this layer until requirements change.
COPY requirements.txt .
RUN pip install --no-cache-dir -r requirements.txt

COPY app.py .

# Don't run as root.
RUN useradd --create-home appuser
USER appuser

ENV STREAMLIT_BROWSER_GATHER_USAGE_STATS=false

EXPOSE 8501

# Hosting platforms like Render pass the port in $PORT; default to 8501 locally.
CMD ["sh", "-c", "streamlit run app.py --server.port=${PORT:-8501} --server.address=0.0.0.0 --server.headless=true"]
