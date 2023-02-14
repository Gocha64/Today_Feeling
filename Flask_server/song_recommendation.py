import Playlist
import Userdata
import readJson


# 노래 추천 알고리즘
def song_recommend(emotion_data : int , userUid : int):
    # playlist의 오브젝트를 반환
    
    """
    1. 감정을 입력받음
    2. 사용자의 해당 감정 -> 사용자의 정보(uid필요) 에 해당하는 장르를 가져옴
    3. 해당 장르의 노래 중 1개를 추천(랜덤으로)
    3.1. 장르를 선택하지 않았다면? -> 아무 장르 중 랜덤으로

    ### Parameters
    1. emotion_data : int
        - 사용자의 감정을 입력받음 -> 화남: 1, 분노: 2, 행복: 3, 슬픔: 4, 놀람: 5
    2. userUid : int
        - 사용자의 uid

    ### Returns
    - Playlist
        - 선택한 장르에 해당하는 Playlist 객체를 반환
    """

    # uid로 userdata로 가져와 dictionary로 변환
    userdata = Userdata.select_userdata_with_uid(userUid).toDict()


    # 해당하는 감정의 장르 데이터 가져오기
    # ex) genreByte: 0000011000
    genreByte = userdata[readJson.getEmotion(emotion_data)]
    genreList = []


    # 바이트 스트링을 playlist 검색에 사용할 수 있도록 변경
    # 101000000 -> [1, 3]
    for i in range(0, len(genreByte)):
        if genreByte[i] == '1':
            genreList.append(i+1)


    # genreList가 비어있으면(아무런 장르가 선택되지 않았다면) 아무 노래나 추천해줌
    if not genreList:
         return Playlist.get_random_playlist()
    
    # genreList에 해당하는 노래를 랜덤으로 추천
    return Playlist.get_random_playlist_from_genreList(genreList)


if __name__ == "__main__":
    print(song_recommend(1,2).urlWeb)

