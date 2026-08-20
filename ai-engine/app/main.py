from fastapi import FastAPI
from fastapi.middleware.cors import CORSMiddleware
from app.api.routes import router as analyze_router

app = FastAPI(title="Device Monitoring AI Engine", version="0.1.0")

# Enable CORS for local development flexibility
app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

app.include_router(analyze_router, prefix="/api/analyze")

@app.get("/health")
async def health_check():
    return {
        "status": "UP",
        "service": "device-monitoring-ai"
    }
