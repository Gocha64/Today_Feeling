from flask import render_template, request, redirect, url_for, session, g, jsonify, current_app
from service.UserQuery import select_user_with_id
import hashlib
from main import bp


@bp.route("/member/login", methods = ['GET', 'POST'])
def login():
    #이미 로그인 했다면 메인페이지
    if request.method == "GET":
        if g.user != None:
            #session.modified = True
            return redirect(url_for("hello"))
        return render_template("login.html")


    if request.method == 'POST':

        get_json_data = request.get_json(True)
        #print(get_json_data)

        userId = get_json_data['userId']
        userPw = get_json_data['userPw']
        userPwHash = str(hashlib.sha1(userPw.encode('utf-8')).hexdigest())

        user = select_user_with_id(current_app, userId)

        if user != None and user.password == userPwHash:
            session['userUid'] = user.uid
            return jsonify({'result' : 'success'})
        else:
            return jsonify({'result' : 'fail'})


# 브라우저에서 세션 쿠키를 제거함
@bp.route("/member/logout")
def logout():
    session.pop('userUid', None)
    return redirect(url_for('main.hello'))





