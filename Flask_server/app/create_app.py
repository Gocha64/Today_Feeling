import os
from dotenv import load_dotenv
from flask import Flask
from flask_cors import CORS, cross_origin
from extensions import db, db_url

application = None

def create_app():
    app = Flask(__name__)
    app.config['SESSION_PERMANENT'] = True

    CORS(app)


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



