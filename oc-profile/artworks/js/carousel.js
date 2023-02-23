/**
 * @typedef CarouselItem 輪播圖輪播項
 * @type {Object}
 * @property {string} serial 系列
 * @property {string} title 標題
 * @property {string} desc 標題下的簡短描述
 * @property {string} thumbnail 輪播圖的圖片
 * @property {string | null} href 按下輪播圖後跳轉的地址
 */

/** @type {Array<CarouselItem>} */
// thumbnail 和 href 在下面的 for 迴圈 做賦值處理
let carouselList = [
  { serial: '01', title: 'TITLE X', desc: '#Original#', thumbnail: 'img/thumbnail/ORIGINAL_01_thumbnail.png', href: 'img/ORIGINAL_01.png' },
  { serial: '02', title: 'TITLE X', desc: '#Original#', thumbnail: 'img/thumbnail/ORIGINAL_02_thumbnail.png', href: 'img/ORIGINAL_02.png' },
  { serial: '03', title: 'TITLE X', desc: '#Original#', thumbnail: 'img/thumbnail/ORIGINAL_03_thumbnail.png', href: 'img/ORIGINAL_03.png' },
  { serial: '04', title: 'TITLE X', desc: '#Original#', thumbnail: 'img/thumbnail/ORIGINAL_04_thumbnail.png', href: 'img/ORIGINAL_04.png' },
  { serial: '05', title: 'TITLE X', desc: '#Original#', thumbnail: 'img/thumbnail/ORIGINAL_05_thumbnail.png', href: 'img/ORIGINAL_05.png' },
  { serial: '06', title: 'TITLE X', desc: '#Operator#', thumbnail: 'img/thumbnail/OPERATOR_01_thumbnail.png', href: 'img/OPERATOR_01.png' },
  { serial: '07', title: 'TITLE X', desc: '#Operator#', thumbnail: 'img/thumbnail/OPERATOR_02_thumbnail.png', href: 'img/OPERATOR_02.png' },
  { serial: '08', title: 'TITLE X', desc: '#Operator#', thumbnail: 'img/thumbnail/OPERATOR_03_thumbnail.png', href: 'img/OPERATOR_03.png' },
  { serial: '09', title: 'TITLE X', desc: '#Operator#', thumbnail: 'img/thumbnail/OPERATOR_04_thumbnail.png', href: 'img/OPERATOR_04.png' },
  { serial: '10', title: 'TITLE X', desc: '#Operator#', thumbnail: 'img/thumbnail/OPERATOR_05_thumbnail.png', href: 'img/OPERATOR_05.png' },
  { serial: '11', title: 'TITLE X', desc: '#Operator#', thumbnail: 'img/thumbnail/OPERATOR_06_thumbnail.png', href: 'img/OPERATOR_06.png' },
  { serial: '12', title: 'TITLE X', desc: '#Operator#', thumbnail: 'img/thumbnail/OPERATOR_07_thumbnail.png', href: 'img/OPERATOR_07.png' },
  { serial: '13', title: 'TITLE X', desc: '#Operator#', thumbnail: 'img/thumbnail/OPERATOR_08_thumbnail.png', href: 'img/OPERATOR_08.png' },
  { serial: '14', title: 'TITLE X', desc: '#Operator#', thumbnail: 'img/thumbnail/OPERATOR_09_thumbnail.png', href: 'img/OPERATOR_09.png' },
  { serial: '15', title: 'TITLE X', desc: '#Autumn#', thumbnail: 'img/thumbnail/AUTUMN_01_thumbnail.png', href: 'img/AUTUMN_01.png' },
  { serial: '16', title: 'TITLE X', desc: '#Autumn#', thumbnail: 'img/thumbnail/AUTUMN_02_thumbnail.png', href: 'img/AUTUMN_02.png' },
  { serial: '17', title: 'TITLE X', desc: '#Autumn#', thumbnail: 'img/thumbnail/AUTUMN_03_thumbnail.png', href: 'img/AUTUMN_03.png' },
  { serial: '18', title: 'TITLE X', desc: '#Autumn#', thumbnail: 'img/thumbnail/AUTUMN_04_thumbnail.png', href: 'img/AUTUMN_04.png' },
  { serial: '19', title: 'TITLE X', desc: '#Urban#', thumbnail: 'img/thumbnail/URBAN_01_thumbnail.png', href: 'img/URBAN_01.png' },
  { serial: '20', title: 'TITLE X', desc: '#Urban#', thumbnail: 'img/thumbnail/URBAN_02_thumbnail.png', href: 'img/URBAN_02.png' },
  { serial: '21', title: 'TITLE X', desc: '#Urban#', thumbnail: 'img/thumbnail/URBAN_03_thumbnail.png', href: 'img/URBAN_03.png' },
  { serial: '22', title: 'TITLE X', desc: '#Urban#', thumbnail: 'img/thumbnail/URBAN_04_thumbnail.png', href: 'img/URBAN_04.png' },
  { serial: '23', title: 'TITLE X', desc: '#Urban#', thumbnail: 'img/thumbnail/URBAN_05_thumbnail.png', href: 'img/URBAN_05.png' },
  { serial: '24', title: 'TITLE X', desc: '#Urban#', thumbnail: 'img/thumbnail/URBAN_06_thumbnail.png', href: 'img/URBAN_06.png' },
  { serial: '25', title: 'TITLE X', desc: '#Urban#', thumbnail: 'img/thumbnail/URBAN_07_thumbnail.png', href: 'img/URBAN_07.png' },
  { serial: '26', title: 'TITLE X', desc: '#Urban#', thumbnail: 'img/thumbnail/URBAN_08_thumbnail.png', href: 'img/URBAN_08.png' },
  { serial: '27', title: 'TITLE X', desc: '#Ancient#', thumbnail: 'img/thumbnail/ANCIENT_01_thumbnail.png', href: 'img/ANCIENT_01.png' },
  { serial: '28', title: 'TITLE X', desc: '#Fashion#', thumbnail: 'img/thumbnail/FASHION_01_thumbnail.png', href: 'img/FASHION_01.png' },
  { serial: '29', title: 'TITLE X', desc: '#Other#', thumbnail: 'img/thumbnail/OTHER_01_thumbnail.png', href: 'img/OTHER_01.png' },
  { serial: '30', title: 'TITLE X', desc: '#Other#', thumbnail: 'img/thumbnail/OTHER_02_thumbnail.png', href: 'img/OTHER_02.png' },
  { serial: '31', title: 'TITLE X', desc: '#Other#', thumbnail: 'img/thumbnail/OTHER_03_thumbnail.png', href: 'img/OTHER_03.png' },
  { serial: '32', title: 'TITLE X', desc: '#Other#', thumbnail: 'img/thumbnail/OTHER_04_thumbnail.png', href: 'img/OTHER_04.png' },
  { serial: '33', title: 'TITLE X', desc: '#Other#', thumbnail: 'img/thumbnail/OTHER_05_thumbnail.png', href: 'img/OTHER_05.png' },
  { serial: '34', title: 'TITLE X', desc: '#Other#', thumbnail: 'img/thumbnail/OTHER_06_thumbnail.png', href: 'img/OTHER_06.png' },
]

// 初始化輪播圖基礎佈局
carouselList.forEach((item, index) => {
  document.querySelector('#media-list').innerHTML += `
  <div 
    class="media-list-item"
    data-index="${index}"
    data-serial="${item.serial}"
    data-title="${item.title}"
    data-desc="${item.desc}"
    data-thumbnail="${item.thumbnail}"
  >
    <div 
      class="media-list-item-img" 
      style="background-image: url(${item.thumbnail})"
      data-title="${item.title}"
    ></div>
  </div>
  `
  document.querySelector('#media-layer-front .media-nav-wrapper').innerHTML += `
  <div
    class="media-nav-item"
    active="${index === 0}"
    data-index="${index}"
    data-serial="${item.serial}"
    data-title="${item.title}"
    data-desc="${item.desc}"
    data-thumbnail="${item.thumbnail}"
  ></div>
  `
})

let activeIndex = 0 // 初始化激活的輪播索引
const layerFront = document.querySelector('#media-layer-front')
const mediaSerial = layerFront.querySelector('.media-info-serial')
const mediaTitle = layerFront.querySelector('.media-info-title')
const mediaDetail = layerFront.querySelector('.media-info-detail')
const mediaMainPic = document.querySelector('#media-layer-view .media-main-pic')
const mediaImage = mediaMainPic.querySelector('.media-img')
const mediaList = document.querySelector('#media-list')

// 初始化輪播資訊
mediaImage.href = carouselList[0].href
mediaImage.style = `background-image: url(${carouselList[0].thumbnail}); transform-origin: left top; transform: scale(1)`
mediaSerial.innerHTML = carouselList[0].serial
mediaTitle.innerHTML = carouselList[0].title
mediaDetail.innerHTML = carouselList[0].desc

/**
 * 設定輪播項間隔、點擊監聽事件
 */
for (let i = 0; i < carouselList.length; i++) {
  let mediaItem = mediaList.querySelector(`.media-list-item:nth-child(${i + 1})`)
  let navItem = layerFront.querySelector(`.media-nav-item:nth-child(${i + 1})`)

  const mediaListItem = mediaList.querySelector(`.media-list-item:nth-child(1)`)
  const mediaListItemWidth = getComputedStyle(mediaListItem).width.replace('px', '')
  let mediaListItemPaddingRight = getComputedStyle(mediaListItem).paddingRight.replace('px', '')

  // 計算x位移之闊度 = media-list-item 的 width + 左右padding
  let xWidth = (parseFloat(mediaListItemWidth) + parseFloat(mediaListItemPaddingRight) * 2).toFixed(0)

  // 顯示前邊四張圖片，其他隱藏
  setSlidePosition(0);

  // 輪播列表 輪播指示標同時添加點擊處理器
  let array = [mediaItem, navItem]
  array.forEach(item => {
    item.addEventListener('click', () => {
      if (parseInt(item.dataset.index) > parseInt(activeIndex)) {
        carouselItemSwitching(i, 'left')
      } else if (parseInt(item.dataset.index) < parseInt(activeIndex)) {
        carouselItemSwitching(i, 'right')
      }
      activeIndex = item.dataset.index
    })
  })
}

/**
 * 左右箭咀click切換輪播事件
 */
const arrowBtnPrev = document.querySelector('#arrow-btn-prev')
const arrowBtnNext = document.querySelector('#arrow-btn-next')

arrowBtnPrev.addEventListener('click', () => {
  activeIndex > 0 ? activeIndex-- : (activeIndex = carouselList.length - 1)
  carouselItemSwitching(activeIndex, 'right')
})

arrowBtnNext.addEventListener('click', () => {
  activeIndex < carouselList.length - 1 ? activeIndex++ : (activeIndex = 0)
  carouselItemSwitching(activeIndex, 'left')
})

/**
 * 輪播項切換動畫
 * @param {number} index 激活的索引
 * @param {'left' | 'right'} direction 動畫方向 (1.'left', 2.'right')
 */
function carouselItemSwitching(index, direction) {
  // 清空舊的輪播指示標激活狀態
  for (let i = 0; i < carouselList.length; i++) {
    layerFront.querySelector(`.media-nav-item:nth-child(${i + 1})`).setAttribute('active', 'false')
  }
  // 激活當前選中的輪播指示標
  layerFront.querySelector(`.media-nav-item:nth-child(${index + 1})`).setAttribute('active', 'true')

  imageZoom(0.25, direction, carouselList[index].thumbnail, carouselList[index].href).then(() => { })
  slideInText(mediaSerial, direction, 0.2, 0.4, carouselList[index].serial).then(() => { })
  slideInText(mediaTitle, direction, 0.2, 0.5, carouselList[index].title).then(() => { })
  slideInText(mediaDetail, direction, 0.2, 0.6, carouselList[index].desc).then(() => { })
  setSlidePosition(index)
}

function sleep(time) {
  return new Promise(resolve => setTimeout(resolve, time))
}

/**
 * 文字滑入動畫
 * @param {HTMLElement} element 要套用動畫的HTML元素
 * @param {'left' | 'right'} direction 方向 (1.'left', 2.'right')
 * @param {number} duration 持續時間
 * @param {number} delay 延遲時間
 * @param {string} newText 滑入過後顯示的文字
 */
async function slideInText(element, direction, duration, delay, newText) {
  let a
  if (direction === 'left') {
    a = -50
  } else if (direction === 'right') {
    a = 50
  }
  element.style.transition = `${duration}s ease-out`

  await sleep(delay * 1000)
  element.style.opacity = `0`
  element.style.transform = `translateX(${a}%)`
  await sleep(duration * 1000)
  element.style.transform = `translateX(${-a}%)`
  element.style.opacity = `0`
  await sleep(duration * 1000)
  element.innerHTML = newText
  element.style.transform = `translateX(0)`
  element.style.opacity = `1`
  await sleep(delay * 1000)
}

/**
 * 圖片切換動畫
 * @param {number} duration 持續時間
 * @param {'left' | 'right'} direction 方向 (1.'left', 2.'right')
 * @param {string} newImg 切換後的圖片
 * @param {string} href 圖片跳轉連結
 */
async function imageZoom(duration, direction, newImg, href) {
  let oldImgTransformOrigin
  let newImgTransformOrigin
  if (direction === 'left') {
    oldImgTransformOrigin = 'left top'
    newImgTransformOrigin = 'right bottom'
  } else if (direction === 'right') {
    oldImgTransformOrigin = 'right bottom'
    newImgTransformOrigin = 'left top'
  }

  mediaMainPic.innerHTML += mediaMainPic.innerHTML
  const mediaOldImg = mediaMainPic.querySelector('.media-img:nth-child(1)')
  const mediaNewImg = mediaMainPic.querySelector('.media-img:nth-child(2)')
  mediaNewImg.href = href
  mediaNewImg.style.backgroundImage = `url(${newImg})`

  mediaOldImg.style.transformOrigin = oldImgTransformOrigin
  mediaOldImg.style.transform = 'scale(1)'
  mediaOldImg.style.transition = `${duration}s`

  mediaNewImg.style.transformOrigin = newImgTransformOrigin
  mediaNewImg.style.transform = 'scale(0)'
  mediaNewImg.style.transition = `${duration}s`

  await sleep(duration * 1000)
  mediaOldImg.style.transform = 'scale(0)'
  mediaNewImg.style.transform = 'scale(1)'
  await sleep(duration * 1000)
  mediaMainPic.innerHTML = mediaNewImg.outerHTML
}

/**
 * 重构后更符合官网风格的轮播
 * @param {number} activeIndex 当前选中的索引
 */
function setSlidePosition(activeIndex) {

  let displayArea = new Array(6);
  let listIndex = activeIndex + 1;

  // 可显示区域
  for (i = 2; i < 6; i++) {
    if (listIndex > carouselList.length) {
      listIndex = 1;
    }
    displayArea[i] = mediaList.querySelector(`.media-list-item:nth-child(${listIndex})`);
    listIndex++;
  }

  // 区域边缘
  if (activeIndex == 0) {
    displayArea[0] = mediaList.querySelector('.media-list-item:nth-last-child(2)');
    displayArea[1] = mediaList.querySelector('.media-list-item:nth-last-child(1)');
  } else {
    displayArea[0] = (carouselList.length - activeIndex + 2 > carouselList.length) ?
      mediaList.querySelector(`.media-list-item:nth-last-child(1)`)
      : mediaList.querySelector(`.media-list-item:nth-last-child(${carouselList.length - activeIndex + 2})`);
    displayArea[1] = mediaList.querySelector(`.media-list-item:nth-last-child(${carouselList.length - activeIndex + 1})`);
  }

  for (let i = 0; i < carouselList.length; i++) {
    let mediaItem = mediaList.querySelector(`.media-list-item:nth-child(${i + 1})`)

    const mediaListItem = mediaList.querySelector(`.media-list-item:nth-child(1)`)
    const mediaListItemWidth = getComputedStyle(mediaListItem).width.replace('px', '')
    let mediaListItemPaddingRight = getComputedStyle(mediaListItem).paddingRight.replace('px', '')

    // 計算x位移之闊度 = media-list-item 的 width + 左右padding
    let xWidth = (parseFloat(mediaListItemWidth) + parseFloat(mediaListItemPaddingRight) * 2).toFixed(0)

    let inSlide = false;
    displayArea.forEach(image => {
      // 查找正在显示的图片
      if (image.dataset.index == mediaItem.dataset.index) {
        if (image.dataset.index == displayArea[0].dataset.index) {    // 左边缘
          mediaItem.style.transform = `translateX(${-xWidth}px)`
          mediaItem.style.opacity = '0'
          mediaItem.style.pointerEvents = 'none'
        } else if (image.dataset.index == displayArea[displayArea.length - 1].dataset.index) {    // 右边缘
          mediaItem.style.transform = `translateX(${xWidth * 5}px)`
          mediaItem.style.opacity = '0'
          mediaItem.style.pointerEvents = 'none'
        } else {    // 正常显示
          let flag = displayArea.indexOf(image);
          mediaItem.style.transform = `translateX(${xWidth * (flag - 1)}px)`
          mediaItem.style.opacity = '1'
          mediaItem.style.pointerEvents = 'auto'
        }
        inSlide = true;
      }
    });

    // 不显示的图片根据位置关系预先设定好位置
    if (!inSlide) {
      if (mediaItem.dataset.index < displayArea[0].dataset.index) {
        mediaItem.style.transform = `translateX(${-xWidth}px)`;
      } else {
        mediaItem.style.transform = `translateX(${xWidth * 5}px)`;
      }
      mediaItem.style.opacity = '0'
      mediaItem.style.pointerEvents = 'auto'
    }
  }
}


class Cursor {
  constructor() {
    this.now = new MouseEvent('');
    this.first = true;
    this.last = 0;
    this.moveIng = false;
    this.fadeIng = false;
    this.nowX = 0;
    this.nowY = 0;
    this.attention = "a,input,button,textarea,.code-header,.gt-user-inner,.navBtnIcon,.wl-sort>li,.vicon,.clickable,.arrowBtn,.media-list-item,.media-nav-item";
    this.set = (X = this.nowX, Y = this.nowY) => {
      this.outer.transform =
        `translate(calc(${X.toFixed(2)}px - 50%),
                calc(${Y.toFixed(2)}px - 50%))`;
    };
    this.move = (timestamp) => {
      if (this.now !== undefined) {
        let delX = this.now.x - this.nowX, delY = this.now.y - this.nowY;
        this.nowX += delX * Math.min(0.025 * (timestamp - this.last), 1);
        this.nowY += delY * Math.min(0.025 * (timestamp - this.last), 1);
        this.set();
        this.last = timestamp;
        if (Math.abs(delX) > 0.1 || Math.abs(delY) > 0.1) {
          window.requestAnimationFrame(this.move);
        }
        else {
          this.set(this.now.x, this.now.y);
          this.moveIng = false;
        }
      }
    };
    this.reset = (mouse) => {
      this.outer.top = '0';
      this.outer.left = '0';
      if (!this.moveIng) {
        this.moveIng = true;
        window.requestAnimationFrame(this.move);
      }
      this.now = mouse;
      if (this.first) {
        this.first = false;
        this.nowX = this.now.x;
        this.nowY = this.now.y;
        this.set();
      }
    };
    this.Aeffect = (mouse) => {
      if (this.fadeIng == false) {
        this.fadeIng = true;
        this.effecter.left = String(mouse.x) + 'px';
        this.effecter.top = String(mouse.y) + 'px';
        this.effecter.transition =
          'transform .5s cubic-bezier(0.22, 0.61, 0.21, 1)\
      ,opacity .5s cubic-bezier(0.22, 0.61, 0.21, 1)';
        this.effecter.transform = 'translate(-50%, -50%) scale(1)';
        this.effecter.opacity = '0';
        setTimeout(() => {
          this.fadeIng = false;
          this.effecter.transition = '';
          this.effecter.transform = 'translate(-50%, -50%) scale(0)';
          this.effecter.opacity = '1';
        }, 500);
      }
    };
    this.hold = () => {
      this.outer.height = '24px';
      this.outer.width = '24px';
      this.outer.background = "var(--theme-cursor-bg)";
    };
    this.relax = () => {
      this.outer.height = '36px';
      this.outer.width = '36px';
      this.outer.background = "unset";
    };
    this.iframeOut = () => {
      this.outer.opacity = '0';
    };
    this.iframeIn = () => {
      this.outer.height = '36px';
      this.outer.width = '36px';
      this.outer.opacity = '1';
    };
    this.pushHolder = () => {
      document.querySelectorAll(this.attention).forEach(item => {
        if (!item.classList.contains('is--active')) {
          item.addEventListener('mouseover', this.hold, { passive: true });
          item.addEventListener('mouseout', this.relax, { passive: true });
        }
      });
      document.addEventListener('mouseover', (e) => {
        e = e ? e : window.event;
        var from = e.relatedTarget || e.toElement;
        if (!from || from.nodeName == "SECTION") {
          this.iframeIn();
        }
      }, { passive: true });
      document.addEventListener('mouseout', (e) => {
        e = e ? e : window.event;
        var from = e.relatedTarget || e.toElement;
        if (!from || from.nodeName == "HTML") {
          this.iframeOut();
        }
      }, { passive: true });
    };
    let node = document.createElement('div');
    node.id = 'cursor-container';
    node.innerHTML = `<div id="cursor-outer"></div><div id="cursor-effect"></div>`;
    document.body.appendChild(node);
    this.outer = getElement('#cursor-outer', node).style;
    this.outer.top = '-100%';
    this.effecter = getElement('#cursor-effect', node).style;
    this.effecter.transform = 'translate(-50%, -50%) scale(0)';
    this.effecter.opacity = '1';
    window.addEventListener('mousemove', this.reset, { passive: true });
    window.addEventListener('click', this.Aeffect, { passive: true });
    this.pushHolder();
    const observer = new MutationObserver(this.pushHolder);
    observer.observe(document, { childList: true, subtree: true });
  }
}
window.onload = () => new Cursor();

function getElement(string, item = document.documentElement) {
  let tmp = item.querySelector(string);
  if (tmp === null) {
    throw new Error("Unknown HTML");
  }
  return tmp;
}
