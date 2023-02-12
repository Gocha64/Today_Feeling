from datetime import timedelta
from flask import Flask, render_template, request, redirect, url_for, g, jsonify, session
from dotenv import load_dotenv
import os

app = Flask(__name__)

load_dotenv()
app.secret_key = os.getenv("APP_SECRETKEY")

import app_register
import app_login
import app_member
import app_statistics
import app_modify


#app.config["PERMANENT_SESSION_LIFETIME"] = timedelta(minutes=5)
app.config['SESSION_PERMANENT'] = True



# 메인 페이지
@app.route('/')
def hello():
    
    if g.user is not None:
        return render_template("index.html", userName = g.user.name)
    return render_template("index.html")


# 통계정보 등록 테스트를 위한 데모 페이지
@app.route('/statPostTest')
def statPostTest():
    return render_template("statPostTest.html")


# 404 에러 페이지로 리다이렉트
@app.errorhandler(404)
def page_not_found(error):
    return render_template('404.html')




testUrl = "/getJsonTest"
testPage = "register_copy.html"

# json 데이터 전달을 확인하기 위한 메소드
@app.route(testUrl, methods = ["GET", "POST"])
def getJsonTest():

    if request.method == "GET":
        return render_template(testPage)

    get_json_data = request.get_json(True)

    for key, value in get_json_data.items():
        print(f"{key} : {value}")

    return jsonify({"result" : get_json_data})


if __name__ == "__main__":

    app.run(host = '0.0.0.0', port = '10081')




