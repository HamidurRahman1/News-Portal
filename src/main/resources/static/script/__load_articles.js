"use strict";

import { publicEndPoints } from "./api_handling/__public_endpoint.js";

const _maindiv = document.querySelector("#app");

fetch(publicEndPoints.articlesEndpoint)
  .then(response => {
    if (!response.ok)
      throw new Error('Network response was not ok');
    return response.json();
  })
  .then(data => {
    data.forEach(createHTMLarticle)
  })
  .catch(error => {
    console.error('There has been a problem with your fetch operation:', error);
  });

fetch(publicEndPoints.commentsEndpoint)
  .then(response => response.json())
  .then(data => console.log(data))

const createHTMLarticle = (article) => {
  let articleHTML = "";
  articleHTML += "<div class = \"card border-primary mb-3\">";
  articleHTML += "<h1 class=\"_articletitle card-header\">" + article.title + "</h1>";
  articleHTML += "<div class = \"card-body\">";
  articleHTML += "<p class= \"_articlebody card-text\">" + article.body + "</p>";
  articleHTML += "</div>";
  _maindiv.insertAdjacentHTML('beforeend', articleHTML);
}