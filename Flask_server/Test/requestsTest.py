import requests
import json


params = {"userId" : "regiTest", "userPw": "regiTest"}
url = "http://localhost:5000/member/login"


def login_setCookieTest():
    response = requests.post(url,
                             data = params)

    #print(type(response))

    print('헤더: \n', response.headers,'\n')
    print('받은 쿠키: \n', response.headers["Set-Cookie"],'\n')
    print('응답: \n', response.text)


def login_CookieTest():
    cookie ={
        'session' : 'eyJfcGVybWFuZW50Ijp0cnVlLCJ1c2VyVWlkIjo2fQ.Y-GyvA.SHeMIKgTcEiupoiVx8HYueyA-ZY'
    }
    url = "http://localhost:5000"

    
    response = requests.get(url, cookies = cookie)

    print(type(response))

    print(response.headers)
    print(response.text)

#login_setCookieTest()
login_CookieTest()

