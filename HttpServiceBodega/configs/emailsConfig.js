module.exports={
    transporterConnectionGmail: {
      host: 'smtp.office365.com', // Office 365 server
      port: 587,     // secure SMTP
      secure: false, // false for TLS - as a boolean not string - but the default is false so just remove this completely
        auth: {
          user: 'cristian.arellano@hultprize.org',
          pass: 'Fonseca1995'
        }
      },
      address: {
        userGmail: 'cristian.arellano@hultprize.org',
        mailMedio1:'mrjleo1989@gmail.com',
        mailMedio2:'jleo_89@hotmail.com'
      },
      templatePDF: {
          pathPDF_1: 'C:/Users/user/Desktop/Cristian Arellano - # Invoice 1 Oct.pdf',
          pathPDF_2: 'https://raw.github.com/nodemailer/nodemailer/master/LICENSE'
      }
}