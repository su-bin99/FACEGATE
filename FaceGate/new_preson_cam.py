import cv2
#import image

    
face_cascade = cv2.CascadeClassifier(cv2.data.haarcascades + 'haarcascade_frontalface_default.xml')

cap = cv2.VideoCapture(0)
cap.set(3,640)
cap.set(4,480)

while(True):
    ret, frame = cap.read()
    frame = cv2.flip(frame,1)
    gray = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)
    
    
    faces = face_cascade.detectMultiScale(gray, 1.05,5)
    
    
    if len(faces):
        for (x,y,w,h) in faces:
            cv2.rectangle(frame,(x,y),(x+w,y+h),(255,0,0),2)
            cv2.imshow('result',frame)
    
    k = cv2.waitKey(30) & 0xff
    
    if k ==27:
        cropped = frame[y - int(h/10):y + h + int(h/10),x - int(w/10):x + w + int(w/10)]
            
        cropped_resize = cv2.resize(cropped,(256,256))
        cv2.imwrite("new_cropped.jpg",cropped_resize)
        break
    
cap.release()
cv2.destroyAllWindows()
    
    
