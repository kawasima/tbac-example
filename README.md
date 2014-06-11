tbac-example
============

The example of token based access control for static contents in a Java web application.

## 目的

Webアプリケーションにおいて、例えば個人のアップロードした画像等のファイルは、自分自身もしくは権限を与えた人のみに制限したいということはよくあります。

権限のチェックなどをふつうに行えばよいのですが、そうなると大抵はDBやLDAPへのアクセスが必要になって、画像が沢山あるページなどを表示するときに、サーバリソースの心配をしなくてはなりません。

そこでサーバリソースに負荷をかけずに、適切なアクセス制御をする仕組みの例を作りました。

## 仕組み

このサンプルアプリケーションでは、このアクセス制御をトークンベースで行っています。

トークンは、

```
有効期限$Base64(HmacSHA256(sessionId$リソースパス$有効期限))

```

で作ります。これをindex.jspでやっているように、画像のURLのリクエストパラメータとしてくっつけます。

そして画像を表示するときに、TBACFilterでトークンのチェックがなされます。

1. トークンがある。
2. セッションがある(認証)。
3. 送られてきたトークンの有効期限より現在日時が小さい。
4. ↑で作られたトークンを同じ手順でトークンを作り、送られてきたものと一致する。

これらを全部パスすれば、DefaultServletを介して画像が表示される。そうでなければ403になります。
トークンに含まれる平文部分の有効期限を改ざんしても、HmacSHA256のところでハッシュ値が一致しないのでエラーにすることができます。
同様にこのトークンをもって、別の画像を表示しようとしても、やはりHmacSHA256のハッシュ値が一致しないのでエラーになります。

こうすることで、サーバリソース消費を抑えながら適切なアクセス制御が実現できます。

## 動かし方

```shell
% mvn compile
% mvn waitt:run
```

