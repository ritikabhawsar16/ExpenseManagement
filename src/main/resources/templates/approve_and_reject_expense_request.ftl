<!DOCTYPE html>
<html>
<head>
    <title>Expense Request Status Notification</title>
   <style>
          body {
              font-family: Arial, sans-serif;
              color: #333;
              margin: 0;
              padding: 0;
          }

          .container {
              max-width: 600px;
              margin: 0 auto;
              padding: 20px;
              background-color: #f5f5f5;
              border-radius: 5px;
          }

          h1 {
              color: #333;
              font-size: 24px;
              margin-bottom: 20px;
          }

          p {
              font-size: 16px;
              line-height: 24px;
              margin-bottom: 20px;
          }

          table {
              border-collapse: collapse;
              width: 100%;
          }

          th, td {
              padding: 10px;
              text-align: left;
              border-bottom: 1px solid #ddd;
          }

          th {
              background-color: #f2f2f2;
              font-weight: bold;
          }

          .button {
              color: #fff;
              display: inline-block;
              padding: 10px 20px;
              background-color: #316fea;
              text-decoration: none;
              border-radius: 4px;
              font-weight: bold;
              margin-right: 10px;
          }

              .button:hover {
              background-color: #1e4bb5;
          }

          .signature {
              margin-top: 45px;
              font-size: 16px;
          }

          .signature p {
              margin: 5px 0;
          }

          .support {
              font-size: 16px;
              margin-top: 20px;
          }

          .support a {
              color: #316fea;
              text-decoration: none;
          }
      </style>
</head>
<body>
    <div class="container" >
        <h1>Expense Request Status Notification</h1>
        <p>
            Dear <span id="Name">${Name}</span>,
            <br>
            <span id="Message">${Message}</span>
        </p>
         <table>
            <tr>
                 <th>Expense Amount</th>
                  <td><span id="ExpenseAmount">${ExpenseAmount}</span></td>
            </tr>
            <tr>
                <th>Expense Date</th>
                <td><span id="ExpenseDate">${ExpenseDate}</span></td>
            </tr>
            <tr>
                 <th>Expense Purpose</th>
                 <td><span id="ExpensePurpose">${ExpensePurpose}</span></td>
            </tr>

        </table>


        <div class="support">
            <p>
                Contact our support team if you have any questions or concerns.
                <br>
                <a href="javascript:void(0);" target="_blank" rel="noopener">teamhr.adt@gmail.com</a>
            </p>
        </div>

        <div class="signature">
            <p>Our best,</p>
            <p>Alphadot Technologies Team</p>
        </div>
    </div>
</body>
</html>
