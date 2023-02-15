from flask import render_template, request, g, jsonify, session, current_app
from main import bp
from service.UserQuery import select_user_with_uid

# 메인 페이지
@bp.route('/')
def hello():
    
    if g.user is not None:
        return render_template("index.html", userName = g.user.name)
    return render_template("index.html")


# 404 에러 페이지로 리다이렉트
@bp.app_errorhandler(404)
def page_not_found(error):
    return render_template('404.html')


@bp.before_request
def load_logged_in_user():
    userUid = session.get('userUid')
    print(session)
    if userUid is None:
        g.user = None
    else:
        session.permanent = True
        g.user = select_user_with_uid(current_app, userUid)
    
    #print(g.user)


testUrl = "/getJsonTest"
testPage = "register.html"

# json 데이터 전달을 확인하기 위한 메소드
@bp.route(testUrl, methods = ["GET", "POST"])
def getJsonTest():

    if request.method == "GET":
        return render_template(testPage)

    get_json_data = request.get_json(True)

    for key, value in get_json_data.items():
        print(f"{key} : {value}")

    return jsonify({"result" : get_json_data})

# 통계정보 등록 테스트를 위한 데모 페이지
@bp.route('/statPostTest')
def statPostTest():
    return render_template("statPostTest.html")








