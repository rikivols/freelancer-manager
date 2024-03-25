import json


def open_json(file_name: str) -> dict:
    with open(file_name, encoding="utf=8") as f:
        return json.load(f)
