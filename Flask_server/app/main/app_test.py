from flask import jsonify, render_template, request
from main import bp

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
    from service.StatisticsQuery import select_statistics_with_uid
    stat = select_statistics_with_uid(68)
    print(stat.dateTime.month)
    return render_template("statPostTest.html")

