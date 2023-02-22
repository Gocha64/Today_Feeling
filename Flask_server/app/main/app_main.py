from flask import render_template, request, session, g
from service.UserQuery import select_user_with_id
from main import bp
from service.UserQuery import select_user_with_uid
import logging

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


# 모든 요청 전에 사용자 확인
@bp.before_request
def load_logged_in_user():
    ip = request.remote_addr

    userUid = session.get('userUid')

    jsonData = request.get_json(force=True,silent=True)
    print(jsonData)
    if userUid is None:
        if request.args.get("userId") != None:
            g.user = select_user_with_id(request.args.get("userId"))
        elif jsonData != None and 'userId' in jsonData:
            g.user = select_user_with_id(jsonData['userId'])
        else:
            g.user = None
        logging.info(f'connected from {ip}')
    else:
        session.permanent = True
        g.user = select_user_with_uid(userUid)
        logging.info(f'connected from {ip}, userId: {g.user.id}')
    
    #print(g.user)









