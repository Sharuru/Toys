var token = localStorage.getItem("_MSUM_LAST_USED_TOKEN");
var lastnameDom = $("#lastname");
var firstnameDom = $("#firstname");
var usernameDom = $("#username");
var nicknameDom = $("#nickname");
var emailDom = $("#email");
var passwordDom = $("#password");

// 检测本地存储
if (token === null) {
    console.log("无本地记录，要求授权。")
} else {
    $("#token").val(token);
}


function autoFill() {
    var usernameVal = pinyinUtil.getPinyin(firstnameDom.val(), '', false, false).toLowerCase()
        .concat('_')
        .concat(pinyinUtil.getPinyin(lastnameDom.val(), '', false, false)).toLowerCase();
    usernameDom.val(usernameVal);

    nicknameDom.val(lastnameDom.val() + firstnameDom.val());

    var emailVal = pinyinUtil.getFirstLetter(firstnameDom.val(), false).toLowerCase()
        .concat('.')
        .concat(pinyinUtil.getPinyin(lastnameDom.val(), '', false, false)).toLowerCase()
        .concat('@kaimadata.cn');
    emailDom.val(emailVal);

    passwordDom.val('KaimaSh.w0rd');
    localStorage.setItem("_MSUM_LAST_USED_TOKEN", $("#token").val());
}
