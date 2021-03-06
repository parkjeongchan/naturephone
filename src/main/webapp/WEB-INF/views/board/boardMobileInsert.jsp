<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- 자바스크립트-->
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"
        integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
    <!-- 부트스트랩 5.0 -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/css/bootstrap.min.css" rel="stylesheet"
        integrity="sha384-BmbxuPwQa2lc/FVzBcNJ7UAyJxM6wuqIj61tLrc4wSX0szH/Ev+nYRRuWlolflfl" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-b5kHyXgcpbZJO/tY9Ul7kGkf1S0CWuKcCD38l8YkeH8z8QjE0GmW1gYU5S9FOnJ0"
        crossorigin="anonymous"></script>
    <!-- Font Awesome5 -->
    <script src="https://use.fontawesome.com/releases/v5.2.0/js/all.js"></script>
    <title>메뉴바</title>
</head>
<style>
    /* 사이드 바*/
    .sideBar {
        text-align: center;
    }
    .sideTitle {
        background-color: lightgray;
        border-color: lightgray;
    }
    .sideContent {
        font-size: 13px;
    }
    .main-div {
        background: #ffffff none repeat scroll 0 0;
        border-radius: 2px;
        margin: 10px auto 30px;
        max-width: 850px;
        padding: 50px 70px 70px 71px;
    }
    .rightSpace a {
        color: rgb(7, 7, 7) !important;
    }
    .rightSpace {
        margin-top: 100px;
    }
    .sideBar li {
        background-color: #56CE7C;
        color: #ffffff;
    }
    .sideTitle{
        font-size: 14px;
        font-weight: bold;
    }
    /* 내용 */
    .btn {
        font-size: 13px;
    }
    span {
        color: rgba(213, 70, 70, 0.809);
    }
    #modifyMemberInfo * {
        font-size: 13px;
    }
      .newsClass {
     border: solid 1px black;
     position: relative;
  }
    [type=file] {
      display : block;
   }
   
   #contentImgArea {
      display :inline-block;
      margin:15px;
   }
  
</style>
<body>
<jsp:include page="../common/menubar.jsp"/>
    <div class="container">
      <div class="row">
          <div class="col-md-2 rightSpace" style="margin-top: 100px;">
              <div class="list-group col-md-10 sideBar">
                   <ul>
                        <li class="list-group-item sideTitle">커뮤니티</li>
                        <li><a href="${ contextPath }/boardMobile/list" class="list-group-item list-group-item-action sideContent" style="background-color:#f1f3f5;">모바일 뉴스</a></li>
                        <li><a href="${ contextPath }/boardFree/list" class="list-group-item list-group-item-action sideContent">자유게시판</a></li>
                        <li><a href="${ contextPath }/boardSurvey/list" class="list-group-item list-group-item-action sideContent">회원 설문</a></li>
                    </ul>
              </div>
          </div>
            <!-- 내용 -->
         <div class="col-md-8 content">
        <div class="main-div">
        <form action="${ contextPath }/boardMobile/insert" id="writeForm" method="post" enctype="multipart/form-data">
          <h5><b>모바일뉴스</b></h5>
            <hr>     
          <table class="table table-bordered">
            <thead>
              <tr>
                <th scope="col" style="text-align: center; width: 20%; background-color:#F4F4F4;">제목</th>
                <td><input type="text" name="btitle" style="width: 70%; border: 1px solid  #C8C8C8;"></td>                                            
              </tr>
     
            </thead>
            <tbody>
                     
            </tbody>
          </table>
          <textarea name="bcontent" style="width: 100%; height: 200px; border: 1px solid  #C8C8C8;"></textarea>
          <br><br>                  
          <h5 class="board_title">이미지 첨부</h5>
			<input type="file" name="uploadFile">		
			<div id="contentImgArea">
				<img id="content1"> 
			</div>
			        
            <hr>
            <!-- <button type="button" class="btn btn-secondary">목록으로</button> -->
            <button type="submit" class="btn btn-success btn-sm" style="float: right;">등록</button>            
            
          </form>
          <button class="btn btn-secondary btn-sm" onclick="location.href='${ contextPath }/boardFree/list'">목록으로</button>
            
        </div>
      </div>     
        </div>
     </div>
     
      <script>
      // 파일 첨부 시 이벤트 설정
      $(function(){
         $("[type=file]").change(function(){
            loadImg(this);
         });
      });
      
      function loadImg(element){
           if(element.files && element.files[0]){          
            var reader = new FileReader();
            reader.onload = function(e){               
               var selector;
               var size;
               
               switch(element.name){              
               case "contentImg1" :
                  selector = "#content1";
                  size = {width : "250px", height : "150px", border : "solid 1px #dadada"};
                  break;
               case "contentImg2" :
                  selector = "#content2";
                  size = {width : "250px", height : "150px", border : "solid 1px #dadada"};
                  break;
               }               
               $(selector).attr("src", e.target.result).css(size);
            }
         reader.readAsDataURL(element.files[0]);            
         }
      }   
   </script>
  <jsp:include page="../common/footer.jsp"/>      
</body>
</html>