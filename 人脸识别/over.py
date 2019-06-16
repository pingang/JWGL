#!usr/bin/python
# -*- coding: utf-8 -*-
import cv2
import numpy as np
import sys
import time
import urllib
import urllib2
import requests
import base64
import operator as op

def post(server_url, params):
    data = urllib.urlencode(params)
    request = urllib2.Request(server_url, data)
    response=urllib2.urlopen(request)
    return response.read()


def local_image(server_url, image_path):

    server_url="http://localhost:8081/es/signIn"
    image_path="li.jpg"
    r_file = open(image_path, "rb")
#    img = pickle.dumps(r_file.read())   #字节对
    img=base64.b64encode(r_file.read()) 
    params = {"img": img}
    result = post(server_url, params)
#    result=json.dumps(text,ensure_ascii=False)
    print result 
#    print post(server_url, params)
    if op.eq(result,"A"):
       frame=cv2.putText(capt,"Don't At SignIn Time!",(100,100),cv2.FONT_HERSHEY_SIMPLEX,2,(255,0,0),1)
    elif op.eq(result,"B"):
       frame=cv2.putText(capt,"Don't Find In The Face WareHouse!",(100,100),cv2.FONT_HERSHEY_SIMPLEX,2,(255,0,0),1)
    elif op.eq(result,"C"):
       frame=cv2.putText(capt,"Don't Have Course!",(100,100),cv2.FONT_HERSHEY_SIMPLEX,2,(255,0,0),1)
    elif op.eq(result,"D"):
       frame=cv2.putText(capt,"Don't Double SignIn!",(100,100),cv2.FONT_HERSHEY_SIMPLEX,2,(255,0,0),1)
    elif op.eq(result,"E"):
       frame= cv2.putText(capt,"SUCCESS!",(100,100),cv2.FONT_HERSHEY_SIMPLEX,2,(255,0,0),1)
    elif op.eq(result,"F"):
       frame= cv2.putText(capt,"FAIL!",(100,100),cv2.FONT_HERSHEY_SIMPLEX,2,(255,0,0),1)
      


cv2.namedWindow("li") #命名一个窗口
cap = cv2.VideoCapture(0)#打开摄像头
success, frame = cap.read()#读取一桢图像，前一个返回值是否成功，后一个返回值是图像本身
color = (0,225,0)#设置人脸框的颜色
classfier = cv2.CascadeClassifier("/home/wangzhao/Desktop/haarcascade_frontalface_default.xml")#定义分类器
while success:
	success, frame = cap.read()
	size = frame.shape[:2]#获取当前桢彩色图像的大小
	image = np.zeros(size,dtype = np.float16)#定义一个与当前桢图像大小相同的灰度图像矩阵
        image = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)#将当前桢图像转换成灰度图像
	cv2.equalizeHist(image,image)#灰度图像进行直方图等距化
	#设定最小图像的大小
	divisor = 8
	h, w = size
	minSize=(int(w/divisor),int(h/divisor))#这里加了一个取整函数
	faceRects = classfier.detectMultiScale(image,1.2,2,cv2.CASCADE_SCALE_IMAGE,minSize)#人脸检测
	if len(faceRects)>0:#人脸数组长度大于0
           for faceRect in faceRects:#对每一个人脸画矩形框
		x,y,w,h = faceRect
		cv2.rectangle(frame,(x,y),(x+w,y+h),color,2)
	 	cv2.imwrite("li.jpg",frame)
                capt=cv2.imread('li.jpg')
                frame=cv2.putText(capt,"",(250,100),cv2.FONT_HERSHEY_SIMPLEX,2,(255,0,0),3)
#                time.sleep(1)
#                发送请求并得到回应
#               如果返回yes
#               如果返回no                
#               time.sleep(2)
#                sys.exit(0)
                url = "http://localhost:8081/li.jpg?"
#               local image
                file_path = "li.jpg"
                local_image(url, file_path)
	cv2.imshow("li",frame)#显示图像
	key = cv2.waitKey(10)
	c = chr(key & 255)
	if c in ['q','Q',chr(27)]:
	   break
cv2.destroyWindow("li")
