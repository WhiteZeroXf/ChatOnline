let curPage = '#index';
let before = '';

function jump(target) {
    hideArea(curPage);
    showArea(target);
    curPage = target;
}

/**
 * 使用出入栈制作返回功能
 */
function back() {
    showArea(curPage);

}