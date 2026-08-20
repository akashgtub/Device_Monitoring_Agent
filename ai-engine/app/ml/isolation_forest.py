from sklearn.ensemble import IsolationForest
import numpy as np

class AnomalyDetector:
    def __init__(self):
        # Using a simple Isolation Forest with sensible defaults for demonstration
        # In a real environment, this model would be trained on historical normal data
        self.model = IsolationForest(contamination=0.1, random_state=42)
        
        # We pre-fit it with some dummy "normal" data so it can predict immediately
        # (Usually you'd load a pre-trained model from disk)
        dummy_normal_data = np.array([
            [20.0, 40.0, 30.0, 45.0],
            [25.0, 45.0, 35.0, 48.0],
            [15.0, 35.0, 25.0, 42.0],
            [30.0, 50.0, 40.0, 50.0],
            [10.0, 30.0, 20.0, 40.0],
        ])
        self.model.fit(dummy_normal_data)

    def detect(self, features: dict) -> tuple[bool, float]:
        """
        Returns (is_anomaly, score)
        """
        # IsolationForest expects a 2D array
        input_data = np.array([[
            features['cpuUsage'],
            features['memoryUsage'],
            features['diskUsage'],
            features['temperature']
        ]])
        
        # Predict: -1 for outliers, 1 for inliers
        prediction = self.model.predict(input_data)[0]
        
        # score_samples returns opposite of anomaly score (lower is more abnormal)
        # We'll normalize it to a 0-1 scale where 1 is highly anomalous.
        # This is a simplification for the foundation.
        raw_score = self.model.score_samples(input_data)[0]
        anomaly_score = max(0.0, min(1.0, -raw_score))
        
        is_anomaly = (prediction == -1)
        
        return is_anomaly, anomaly_score
