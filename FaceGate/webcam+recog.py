from PIL import Image
#import matplotlib.pyplot as plt
import glob
import tensorflow as tf
import numpy as np
import re
import os

#전역변수
image_size = 256
image_color = 3
maxpool_filter_size = 2
num_classes = 6 #분류하는 사람 수
#batch_size = 100
learning_rate = 0.0001


#filter
#conv1 - 3*3*3 필터 16개, image size 256
#conv2 - 3*3*16 필터 32개, image size 128
#conv3 - 3*3*32 필터 64개, image size 64
#conv4 - 5*5*64 필터 128개, image size 32
#max pooling - 모두 2*2로 stride 2씩. maxpooling stride가 2 stride라 이미지 size 반 씩 줄어듬

#학습 정도에 따라 필터 개수나 사이즈를 조정해주세요

#convolutional network layer 1
def conv1(input_data):
    #layer 1 설정값(convolutional layer)
    conv1_filter_size = 3
    conv1_layer_size = 16
    stride1 = 1
    
    with tf.name_scope('conv1'):
        #filter define
        W_conv1 = tf.Variable(tf.truncated_normal([conv1_filter_size,
                                                   conv1_filter_size,
                                                   image_color,
                                                   conv1_layer_size],
                                                   stddev=0.1))
        #bias
        b1 = tf.Variable(tf.truncated_normal([conv1_layer_size],stddev=0.1))
       
        #filter apply
        #convolutional network define
        h_conv1 = tf.nn.conv2d(input_data,W_conv1,strides=[1,1,1,1],padding='SAME')
        #활성함수(Activation function)
        h_conv1_relu = tf.nn.relu(tf.add(h_conv1,b1))
        #Max Pooling
        h_conv1_maxpool = tf.nn.max_pool(h_conv1_relu,
                                        ksize=[1,2,2,1],
                                        strides = [1,2,2,1],
                                        padding='SAME')
    
    return h_conv1_maxpool

#convolutional network layer2
def conv2(input_data):
    #layer 2 설정값
    conv2_filter_size = 3
    conv2_layer_size = 32
    conv1_layer_size = 16
    stride2 = 1
    
    with tf.name_scope('conv_2'):
        #filter define
        W_conv2 = tf.Variable(tf.truncated_normal([conv2_filter_size,
                                                   conv2_filter_size,
                                                   conv1_layer_size,
                                                   conv2_layer_size],
                                                   stddev=0.1))
        #bias
        b2 = tf.Variable(tf.truncated_normal([conv2_layer_size],
                                             stddev=0.1))
       
        #filter apply
        #convolutional network define
        h_conv2 = tf.nn.conv2d(input_data,W_conv2,strides=[1,1,1,1],padding='SAME')
        #활성함수(Activation function)
        h_conv2_relu = tf.nn.relu(tf.add(h_conv2,b2))
        #Max Pooling
        h_conv2_maxpool = tf.nn.max_pool(h_conv2_relu,
                                        ksize=[1,2,2,1],
                                        strides = [1,2,2,1],
                                        padding='SAME')
    
    return h_conv2_maxpool


#convolutional network layer3
def conv3(input_data):
    conv3_filter_size = 3
    conv3_layer_size = 64
    conv2_layer_size = 32
    stride3 = 1
    
    with tf.name_scope('conv_3'):
        #filter define
        W_conv3 = tf.Variable(tf.truncated_normal([conv3_filter_size,
                                                   conv3_filter_size,
                                                   conv2_layer_size,
                                                   conv3_layer_size],
                                                   stddev=0.1))
        #bias
        b3 = tf.Variable(tf.truncated_normal([conv3_layer_size],
                                             stddev=0.1))
       
        #filter apply
        #convolutional network define
        h_conv3 = tf.nn.conv2d(input_data,W_conv3,strides=[1,1,1,1],padding='SAME')
        #활성함수(Activation function)
        h_conv3_relu = tf.nn.relu(tf.add(h_conv3,b3))
        #Max Pooling
        h_conv3_maxpool = tf.nn.max_pool(h_conv3_relu,
                                        ksize=[1,2,2,1],
                                        strides = [1,2,2,1],
                                        padding='SAME')
    
    return h_conv3_maxpool

#convolutional network layer 4
def conv4(input_data):
    conv4_filter_size = 5
    conv4_layer_size = 128
    conv3_layer_size = 64
    stride4 = 1
    
    with tf.name_scope('conv_4'):
        W_conv4 = tf.Variable(tf.truncated_normal([conv4_filter_size,
                                                   conv4_filter_size,
                                                   conv3_layer_size,
                                                   conv4_layer_size],
                                              stddev=0.1))
        b4 = tf.Variable(tf.truncated_normal([conv4_layer_size],
                                             stddev=0.1))
        h_conv4 = tf.nn.conv2d(input_data,W_conv4,strides=[1,1,1,1],padding='SAME')
        h_conv4_relu = tf.nn.relu(tf.add(h_conv4,b4))
        h_conv4_maxpool = tf.nn.max_pool(h_conv4_relu
                                        ,ksize=[1,2,2,1]
                                        ,strides=[1,2,2,1],padding='SAME')
        
        
    return h_conv4_maxpool

#fully connected layer 1
def fc1(input_data):
    #128 = conv4 layer size
    input_layer_size = 16*16*128
    fc1_layer_size = 512
    
    with tf.name_scope('fc_1'):
        #앞에서 입력받은 다차원 텐서를 FCC에 넣기 위해 1차원으로 변환
        input_data_reshape = tf.reshape(input_data,[-1,input_layer_size])
        W_fc1 = tf.Variable(tf.truncated_normal([input_layer_size,fc1_layer_size],stddev=0.1))
        b_fc1 = tf.Variable(tf.truncated_normal(
                        [fc1_layer_size],stddev=0.1))
        h_fc1 = tf.add(tf.matmul(input_data_reshape,W_fc1) , b_fc1) 
        
        # h_fc1 = input_data*W_fc1 + b_fc1
        h_fc1_relu = tf.nn.relu(h_fc1)
        
    return h_fc1_relu

#fully connected layer 2
def fc2(input_data):
    fc1_layer_size = 512
    fc2_layer_size = 256
    
    with tf.name_scope('fc_2'):
        W_fc2 = tf.Variable(tf.truncated_normal([fc1_layer_size,fc2_layer_size],stddev=0.1))
        b_fc2 = tf.Variable(tf.truncated_normal(
                        [fc2_layer_size],stddev=0.1))
        h_fc2 = tf.add(tf.matmul(input_data,W_fc2) , b_fc2) # h_fc1 = input_data*W_fc1 + b_fc1
        h_fc2_relu = tf.nn.relu(h_fc2)
    
    return h_fc2_relu

#final layer
# 최종 레이어에 softmax 함수는 적용하지 않았다. 
# 카테고리(인물)에 대한 확률로 결과를 낸다
def final_out(input_data):
    
    with tf.name_scope('final_out'):
        W_fo = tf.Variable(tf.truncated_normal([256,num_classes],stddev=0.1))
        b_fo = tf.Variable(tf.truncated_normal(
                        [num_classes],stddev=0.1))
        h_fo = tf.add(tf.matmul(input_data,W_fo) , b_fo) # h_fc1 = input_data*W_fc1 + b_fc1
        
    return h_fo

#전체 네트워크 모델 정의
#계층을 묶는다
def build_model(images,keep_prob):
    # define CNN network graph
    # output shape will be (*,48,48,16)
    r_cnn1 = conv1(images) # convolutional layer 1
    print ("shape after cnn1 ",r_cnn1.get_shape())
    
    # output shape will be (*,24,24,32)
    r_cnn2 = conv2(r_cnn1) # convolutional layer 2
    print ("shape after cnn2 :",r_cnn2.get_shape() )
    
    # output shape will be (*,12,12,64)
    r_cnn3 = conv3(r_cnn2) # convolutional layer 3
    print ("shape after cnn3 :",r_cnn3.get_shape() )

    # output shape will be (*,6,6,128)
    r_cnn4 = conv4(r_cnn3) # convolutional layer 4
    print ("shape after cnn4 :",r_cnn4.get_shape() )
    
    # fully connected layer 1
    r_fc1 = fc1(r_cnn4)
    print ("shape after fc1 :",r_fc1.get_shape() )

    # fully connected layer2
    r_fc2 = fc2(r_fc1)
    print ("shape after fc2 :",r_fc2.get_shape() )
    
    # drop out
    # 트레이닝시에는 keep_prob < 1.0 , Test 시에는 1.0으로 한다. 
    r_dropout = tf.nn.dropout(r_fc2,keep_prob)
    print ("shape after dropout :",r_dropout.get_shape() ) 
    
    # final layer
    r_out = final_out(r_dropout)
    print ("shape after final layer :",r_out.get_shape() )

 
    return r_out 

def main():
    tf.reset_default_graph()
    images = tf.placeholder(tf.float32,[None,image_size,image_size,image_color])
    labels = tf.placeholder(tf.float32,[None, num_classes])
    keep_prob = tf.placeholder(tf.float32) # dropout ratio

    
    prediction = tf.nn.softmax(build_model(images,keep_prob))
    hypothesis = tf.nn.softmax(prediction)
    sess = tf.InteractiveSession()
    sess.run(tf.global_variables_initializer())
    saver = tf.train.Saver()
    


    import cv2

    #xml = 'haarcascade_frontalface_default.xml'
    face_cascade = cv2.CascadeClassifier(cv2.data.haarcascades + 'haarcascade_frontalface_default.xml')

    cap = cv2.VideoCapture(0)
    cap.set(3,640)
    cap.set(4,480)

    while(True):
        ret, frame = cap.read()
        frame = cv2.flip(frame,1)
        gray = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)
    
    
        faces = face_cascade.detectMultiScale(gray, 1.05,5)
        #print("number of faces detected:" + str(len(faces)))
    
        if len(faces):
            for (x,y,w,h) in faces:
                cv2.rectangle(frame,(x,y),(x+w,y+h),(255,0,0),2)
                cv2.imshow('result',frame)
    
        k = cv2.waitKey(30) & 0xff
    
        if k ==27:
            cropped = frame[y - int(h/10):y + h + int(h/10),x - int(w/10):x + w + int(w/10)]
            
            cropped_resize = cv2.resize(cropped,(256,256))
            cv2.imwrite("web_cam_cropped.jpg",cropped_resize)
            break
    
    cap.release()
    cv2.destroyAllWindows()
    
    x = []
    img = Image.open('web_cam_cropped.jpg')
    img = img.convert("RGB")
    data = np.asarray(img)
    x.append(data)
    x = np.array(x)
    x = x.astype('float32')/255
    print(x.shape)
    
    with tf.Session() as sess:
        saver.restore(sess,'model.ckpt')
        is_correct = tf.equal(tf.argmax(hypothesis,1),tf.argmax(labels,1))
       
        p_val = sess.run(prediction,feed_dict={images:x,keep_prob:1.0})
        name_labels = ['bsb','chs','jhr','ksm','lgh','yc']
        
        i = 0
        for p in p_val[0]:
            print('%s %f'% (name_labels[i],float(p)) )
            
            if (float(p) >0.5):
                f = open('face_recog_result.txt','w')
                f.write(name_labels[i])

                java = i+1
                
                f.close()
            
            i = i + 1
        print('%d' % java)    
main()




   



    
    






