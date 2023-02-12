from flask import Flask, render_template, request, redirect, url_for, g, jsonify
from flask_bcrypt import Bcrypt
import User
import hashlib

from __main__ import app


@app.route('/member/modify', methods=["GET", "POST"])
def modify():
    if request.method == "GET":
        if g.user == None:
            return redirect(url_for("hello"))
        return render_template("modify.html", userId = g.user.id)

    if request.method == "POST":
    # bcrypt = Bcrypt(app)

        get_json_data = request.get_json(True)
        # print(get_json_data)

        userID = g.user.id
        userPw = get_json_data['userPw']
        userName = get_json_data['userName']
        userSex = get_json_data['userSex']

        # print(userId, userPw, userName, userSex)

        # sha1으로 해싱됨
        userPwHash = str(hashlib.sha1(userPw.encode('utf-8')).hexdigest())
        # userPwHash = bcrypt.generate_password_hash(userPw)

        userModi = User.User(userID, userPwHash, userName, userSex)

        if User.select_user_with_name(userName) != None:
            return jsonify({'result': 'overlaped Name'})

        else:
            try:
                User.update_user_with_id(userID, userModi)
                print("changed")
                return jsonify({'result': 'success'})
            except:
                return jsonify({'result': 'undefined Error'})




