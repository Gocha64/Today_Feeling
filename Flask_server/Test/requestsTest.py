import requests
import json

cookie = {
    'session' : 'eyJfcGVybWFuZW50Ijp0cnVlLCJ1c2VybmFtZSI6InRlc3QifQ.Y-BNOg.L6gJAdC0Dlp9E0jDVgTqV4MqJGk'
    }
url = "http://localhost:5000/helloTest"

response = requests.get(url, cookies = cookie)

print(type(response))
print(response.text)
