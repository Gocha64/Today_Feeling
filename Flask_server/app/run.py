from create_app import create_app

application = None
if __name__ == "__main__":
    application = create_app()
    # app.run(host = '0.0.0.0', port = '10081')
    application.run(debug=True)
