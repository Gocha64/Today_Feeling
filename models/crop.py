# import required libraries
import cv2
import glob
category = ["fear", "neutral", "sadness", "surprise"] # "fear", "neutral", "sadness", "surprise"]

for i in category:
   file_path = glob.glob(f"/mnt/d/emo/한국인 감정인식을 위한 복합 영상/RealTraining/{i}/*.jpg")
   k = 0
   for j in file_path:
      # read the input image
      img = cv2.imread(j)
      k += 1
      # convert to grayscale of each frames
      gray = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)

      # read the haarcascade to detect the faces in an image
      face_cascade = cv2.CascadeClassifier('/home/hancom/medium/haarcascade_frontalface_default.xml')

      # detects faces in the input image
      faces = face_cascade.detectMultiScale(gray, 1.3, 4, minSize = (200, 200))
      print('Number of detected faces:', len(faces))

      # loop over all detected faces
      if len(faces) > 0:
         for (x, y, w, h) in faces:
            # To draw a rectangle in a face
            # cv2.rectangle(img, (x, y), (x + w, y + h), (0, 255, 255), 2)
            face = img[y:y + h, x:x + w]
            cv2.imwrite(f'/mnt/d/emo/한국인 감정인식을 위한 복합 영상/cropTraining/{i}/{i}{k}.jpg', face)
