// 检测本地存储
let token = localStorage.getItem("_MSUM_LAST_USED_TOKEN");
if (localStorage.getItem("_MSUM_LAST_USED_TOKEN") === null) {
    console.log("无本地记录，要求授权。")
}else{
    $("#token").val(token);
}
