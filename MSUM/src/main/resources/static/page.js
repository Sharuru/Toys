let accessGrantButton = $("#access-grant-button");

// 检测本地存储
if (localStorage.getItem("_MSUM_LAST_USED_TOKEN") === null) {
    console.log("无本地记录，要求授权。")
}

accessGrantButton.on('click', function () {
    $.ajax({
        method: "post",
        url: "/biz/grantAccess",
        data: {token: $("#access-token").val().trim()}
    })
        .done(function (msg) {
            alert("Data Saved: " + msg);
        });
});
