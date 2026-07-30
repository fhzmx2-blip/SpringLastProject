<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>상품 상세보기</title>
<style type="text/css">
.row {
	margin: 0px auto;
	width: 960px;
}

p {
	overflow: hidden;
	white-space: nowrap;
	text-overflow: ellipsis;
}
</style>
</head>
<body>

  <div class="container">
    <div class="row">
      <table class="table">
        <tr>
          <td width="35%" class="text-center" rowspan="6">
            <img src="${vo.goods_poster}" style="width: 290px; height: 250px; object-fit: cover;">
          </td>
          <td colspan="2">
            <h3>${vo.goods_name}</h3>
            <p style="color:gray;">${vo.goods_sub}</p>
          </td>
        </tr>
        <tr>
          <td width="20%" style="color:gray">할인율</td>
          <td width="45%" style="color:red; font-weight:bold;">${vo.goods_discount}</td>
        </tr>
        <tr>
          <td width="20%" style="color:gray">판매가</td>
          <td width="45%" style="font-weight:bold;">${vo.goods_price}</td>
        </tr>
        <tr>
          <td width="20%" style="color:gray">첫 구매가</td>
          <td width="45%">${vo.goods_first_price}</td>
        </tr>
        <tr>
          <td width="20%" style="color:gray">배송비</td>
          <td width="45%">${vo.goods_delivery}</td>
        </tr>
        <tr>
          <td colspan="2" class="text-right">
            <a href="../goods/main.do" class="btn btn-sm btn-danger">목록</a>
          </td>
        </tr>
      </table>
    </div>
  </div>
</body>

</html>