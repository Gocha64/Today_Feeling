from flask import Flask, render_template, request, redirect, url_for, session, g, jsonify
import User
from datetime import timedelta
import hashlib

from __main__ import app



@app.route("/member/login", methods = ['GET', 'POST'])
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


        user = User.select_user_with_id(userId)
        print(user.uid)

        if user != None and user.password == userPwHash:
            session['userUid'] = userId
            return jsonify({'result' : 'success'})
        else:
            return jsonify({'result' : 'fail'})




# 브라우저에서 세션 쿠키를 제거함
@app.route("/member/logout")
def logout():
    session.pop('userUid', None)
    return redirect(url_for('hello'))


@app.before_request
def load_logged_in_user():
    userUid = session.get('userUid')
    print(session)
    if userUid is None:
        g.user = None
    else:
        session.permanent = True
        g.user = User.select_user_with_uid(userUid)
    
    #print(g.user)



