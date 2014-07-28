import web
from web import form

render = web.template.render('templates/')

#url跳转
urls = (
        '/', 'Index',
        '/viewPost', 'ViewPost',
        '/newPost', 'NewPost',
        '/deletePost', 'DeletePost'
)

#form表单
##新留言表单
newPostForm = form.Form(
                        form.Textbox('Your Name'),
                        form.Textarea('Message'),
                        form.Button('Post it!')
)

class Index:
    def GET(self):
        post = newPostForm()
        return render.index()

class NewPost:
    def GET(self):
        post = newPostForm()
        return render.newPost(post)

if __name__ == "__main__":
    app = web.application(urls, globals())
    app.run()