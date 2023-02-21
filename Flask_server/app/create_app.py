import os
from dotenv import load_dotenv
from flask import Flask
from flask_cors import CORS, cross_origin
from extensions import db, db_url
import logging
from datetime import datetime

application = None

def create_app():
    # 로그 저장할 폴더 검사 및 생성
    if not os.path.isdir("logs"):
        os.makedirs("logs")

    # 로깅 설정
    logging.basicConfig(filename = f"logs/FeelingManager_{datetime.today().strftime('%Y-%m-%d')}.log", level = logging.DEBUG)

    app = Flask(__name__)
    app.config['SESSION_PERMANENT'] = True
    CORS(app, supports_credentials=True, origins=["localhost:3000", "fm.jcopy.net:10085"])


    load_dotenv()
    app.secret_key = os.getenv("APP_SECRETKEY")

    # db 설정
    app.config["SQLALCHEMY_DATABASE_URI"] = db_url
    db.init_app(app)


    # blueprints 등록
    from main import bp as main_bp
    app.register_blueprint(main_bp)

    global application
    application = app

    return app

def get_app():
    global application
    return application



