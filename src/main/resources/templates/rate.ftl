<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <style>
        table, th, td {border: 1px solid black; border-collapse: collapse; border-spacing: 5px;}
        th, td {padding: 3px;}
    </style>
    <title>Document</title>
</head>
<body>
    <div>КУРСЫ ВАЛЮТ</div>
    <div>
        <form method="get">
            <input type="text" name="code" placeholder="code" value="${code?ifExists}">
            <input type="date" name="date" placeholder="" value="${date?ifExists}">
            <button type="submit">FIND</button>
        </form>
    </div>
<br>
    <div>
        <table style="border: 1px solid black; border-spacing: 5px;">
            <tr>
                <td>Currency name</td>
                <td>Bank code</td>
                <td>Rate NBU</td>
                <td>Sale rate PB</td>
                <td>Purchase rate PB</td>
                <td>Date</td>
            </tr>
            <#list rates as rate>
            <tr>
                <td><span>${rate.currencyName}</span></td>
                <td><span>${rate.bankCode}</span></td>
                <td><strong>${rate.rateNBU}</strong></td>
                <td><strong>${rate.saleRate}</strong></td>
                <td><strong>${rate.purchaseRate}</strong></td>
                <td><span>${rate.exchangeDate}</span></td>
            </tr>
            <#else>
            Current exchange rate is not available.
            </#list>
        </table>
    </div>
</body>
</html>