
import requests


class Request:

    def __init__(self, base_url: str, token: str):
        self.url_base = base_url
        self.headers = {
            "Authorization": f"Bearer {token}"
        }

    def req_get(self, path, **kwargs) -> dict | list:
        return requests.get(self.url_base + path, headers=self.headers, **kwargs).json()

    def req_post(self, path, json, **kwargs) -> dict:
        return requests.post(self.url_base + path, json=json, headers=self.headers, **kwargs).json()

    def req_delete(self, path, **kwargs) -> dict:
        return requests.delete(self.url_base + path, headers=self.headers, **kwargs).json()
