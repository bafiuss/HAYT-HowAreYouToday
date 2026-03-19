from transformers import pipeline
from flask import Flask, request, jsonify

app = Flask(__name__)
nlp = pipeline("text-classification", model="bareeraqrsh/Sentiment-analysis-tool")

@app.route("/analyze", methods=["POST"])
def analyze():
    data = request.json
    result = nlp(data["text"])[0]
    return jsonify(result)

if __name__ == "__main__":
    app.run(host="0.0.0.0", port=5000)
