(()=>{"use strict";var e={363:e=>{e.exports=React}},t={};function r(n){var o=t[n];if(void 0!==o)return o.exports;var u=t[n]={exports:{}};return e[n](u,u.exports,r),u.exports}(()=>{var e=function(e,r){if(e&&e.__esModule)return e;if(null===e||"object"!=typeof e&&"function"!=typeof e)return{default:e};var n=t(r);if(n&&n.has(e))return n.get(e);var o={},u=Object.defineProperty&&Object.getOwnPropertyDescriptor;for(var c in e)if("default"!==c&&Object.prototype.hasOwnProperty.call(e,c)){var i=u?Object.getOwnPropertyDescriptor(e,c):null;i&&(i.get||i.set)?Object.defineProperty(o,c,i):o[c]=e[c]}return o.default=e,n&&n.set(e,o),o}(r(363));function t(e){if("function"!=typeof WeakMap)return null;var r=new WeakMap,n=new WeakMap;return(t=function(e){return e?n:r})(e)}const n=({visible:t,close:r,theme:n,subMenu:o})=>((0,e.useEffect)((()=>{const e=document.querySelector("header");if(e){const t=e.querySelector('div[class^="Badge"]');t&&t.remove()}const t=new MutationObserver((e=>{document.querySelectorAll('li[role="menuitem"]').forEach((e=>{e.textContent.includes("FREE EDITION")&&e.remove()}))}));return t.observe(document.body,{childList:!0,subtree:!0}),()=>{t.disconnect()}}),[]),null);window.registerPlugin("me.sharuru.custom-patch",new class{initialize(e,t){e.registerRootComponent(n)}})})()})();