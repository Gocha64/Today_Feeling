from flask import Flask
from flask_sqlalchemy import SQLAlchemy
from datetime import datetime

# create the extension
db = SQLAlchemy()
# create the app
app = Flask(__name__)

app.config["SQLALCHEMY_DATABASE_URI"] = "mariadb+pymysql://root:0000@localhost:3306/shopping_mall"
# initialize the app with the extension
db.init_app(app)

class Board(db.Model):
    __tablename__ = 'boards'
    board_id = db.Column(db.Integer, primary_key = True, autoincrement = True)
    board_title = db.Column(db.String(100), nullable = False)
    board_content = db.Column(db.Text)
    board_register_date = db.Column(db.DateTime(timezone=True), default=datetime.now())

    def __init__(self, board_title, board_content):
        self.board_title = board_title
        self.board_content = board_content

    def __str__(self):
        return "board_id: " + str(self.board_id) + "\t board_title: " + self.board_title


def create_table():
    with app.app_context():
        db.create_all()

def select_board_with_id(id):
    with app.app_context():
        user = db.session.query(Board).filter(Board.board_id == id).first()

    return user

def select_board_all():
    with app.app_context():
        boardlist = db.session.query(Board).all()
    return boardlist

def select_board_paging(page, rows_per_page = 20):
    with app.app_context():
        boardlist = db.session.query(Board).order_by(Board.board_id.desc()).paginate(page = page, per_page = rows_per_page)
    return boardlist

def insert_board(board):
    try:
        with app.app_context():
            db.session.add(board)
            db.session.commit()
    except Exception as e:
        print(e.args)

def delete_board_with_id(id):
    try:
        with app.app_context():
            db.session.query(Board).filter(Board.board_id == id).delete()
            db.session.commit()
    except Exception as e:
        print(e.args)



# def insert_dummy_article():
#     try:
#         with app.app_context():
#             for i in range(101, 155):
#                 title = f'dummytitle{i}'
#                 content = f'dummycontent{i}'
#                 b = Board(title, content)
#                 db.session.add(b)
#                 db.session.commit()

#     except Exception as e:
#         print(e.args)


if __name__ == "__main__":
    #create_table()
    #insert_dummy_article()
    pass