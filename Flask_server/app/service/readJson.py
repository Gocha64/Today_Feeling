import json

# 클로저를 쓸 필요가 있는가 그냥 전역변수로 하면?

# json 파일을 읽음
def readJson(fileName):
    with open(fileName, 'r') as file:
        jsonFile = json.load(file)
        print(f"read {fileName}...")
    def getJson():
        return jsonFile
    return getJson

genrefile = None
emotionfile = None


def getGenre(genreID):
    """
     장르 번호로 장르 이름을 반환

     1: classic
     2: jazz
     3: pop
     4: trot
     5: idol
     6: rock
     7: ballad
     8: game
     9: hiphop
     10: extra
    """
    if type(genreID) == int:
        genreID = str(genreID)

    global genrefile
    if not genrefile:
        genrefile = readJson("./static/Json/genre.json")

    return genrefile()[genreID]

def getEmotion(emotionID):
    """
    감정 번호로 감정 이름을 반환

    1: anger
    2: fear
    3: happiness
    4: sad
    5: surprise
    """
    if type(emotionID) == int:
        emotionID = str(emotionID)

    global emotionfile
    if not emotionfile:
        emotionfile = readJson("./static/Json/emotion.json")

    return emotionfile()[emotionID]
