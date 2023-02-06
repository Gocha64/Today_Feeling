from flask import Flask, render_template, request, redirect, url_for, g, jsonify
from flask_bcrypt import Bcrypt
import User
import hashlib

from __main__ import app



@app.route('/member/register', methods=["GET", "POST"])
def register():
    if request.method == "GET":
        #이미 로그인 했다면 메인페이지
        if g.user != None:
            return redirect(url_for("hello"))

        return render_template("register.html")

    if request.method == "POST":
        #bcrypt = Bcrypt(app)

        print(request.form)

        userId = request.form.get('userId')
        userPw = request.form.get('userPw')
        userName = request.form.get('userName')
        userSex = request.form.get('userSex')

        #print(userId, userPw, userName, userSex)


        # sha1으로 해싱됨
        userPwHash = str(hashlib.sha1(userPw.encode('utf-8')).hexdigest())
        #userPwHash = bcrypt.generate_password_hash(userPw)

        user = User.User(userId, userPwHash, userName, userSex)
        if User.select_user_with_id(userId) != None:
            return jsonify({'result' : 'overlaped ID'})
        
        elif User.select_user_with_name(userName) != None:
            return jsonify({'result' : 'overlaped Name'})
        
        else:
            try:
                User.insert_user(user)
                print(f"{userId} registered")
                return jsonify({'result' : 'success'})
            except:
                return jsonify({'result' : 'undifiend Error'})

    
    
    
