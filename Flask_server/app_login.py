from flask import Flask, render_template, request, redirect, url_for, session, g, jsonify
import User
from flask_bcrypt import Bcrypt
from datetime import timedelta

from __main__ import app


app.config["PERMANENT_SESSION_LIFETIME"] = timedelta(minutes=3)

@app.route("/login", methods = ['GET', 'POST'])
def login():
    #이미 로그인 했다면 메인페이지
    if g.user != None:
        return redirect(url_for("hello"))

    if request.method == "GET":
        return render_template("login.html")

    if request.method == 'POST':
        bcrypt = Bcrypt(app)
        user_name = request.form.get('userName')
        user_pw = request.form.get('userPassword')
        user = User.select_user_with_name(user_name)

        if user != None and bcrypt.check_password_hash(user.user_password, user_pw):
            session['username'] = user_name
            return jsonify({'result' : 'success'})

        return jsonify({'result' : 'fail'})


@app.route("/logout")
def logout():
    session.pop('username', None)
    return redirect(url_for('hello'))

@app.before_request
def load_logged_in_user():
    user_name = session.get('username')
    #print(session)
    if user_name is None:
        g.user = None
    else:
        g.user = User.select_user_with_name(user_name)
    
    #print(g.user)



