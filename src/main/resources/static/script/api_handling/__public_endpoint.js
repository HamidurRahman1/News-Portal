import { PUBLIC_DOMAIN } from "./__domain_url.js";

const _articles_path = "/articles";
const _comments_path = "/comments";
const _articles_noauthor = "/articles/no-author";

const _login = "/login";
const __sign_up = "/user/signup";

const signUpEndpoint = PUBLIC_DOMAIN + __sign_up;

const publicEndPoints = {
  articlesEndpoint: PUBLIC_DOMAIN + _articles_path,
  noAuthorArticlesEndpoint: PUBLIC_DOMAIN + _articles_noauthor,
  commentsEndpoint: PUBLIC_DOMAIN + _comments_path,
  getArticleById: function (id) {
    return PUBLIC_DOMAIN + ("/article/" + a_id + "/comments");
  },
  loginEndpoint: PUBLIC_DOMAIN + _login,
};

export { publicEndPoints, signUpEndpoint };
