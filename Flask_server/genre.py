import json

# 클로저를 쓸 필요가 있는가 그냥 전역변수로 하면?

# json 파일을 읽음
def readJson():
    with open("./genre.json", 'r') as file:
        jsonFile = json.load(file)
    def getJson():
        return jsonFile
    return getJson


# 장르의 id로 장르 이름을 출력
def getGenre(genreID : str):
    jsonfile = readJson()
    print(jsonfile()[genreID])

getGenre('5')
