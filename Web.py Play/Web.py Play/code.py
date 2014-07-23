import web
import string

render = web.template.render('templates/')

urls = (
  '/(.*)', 'sites'
)

class sites:
    def GET(self, dest):
        if dest:
            return render.template(dest)
        else:
            return "This is HomePage"

if __name__ == "__main__":
    app = web.application(urls, globals())
    app.run()