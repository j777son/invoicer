<html>

<head>
	<title>Engineworks Invoice No. ${invoice.number}</title>
	<style>
		
		* {
			/*border: 0.5px solid black;*/
			font-family: 'Helvetica';
		}

		@page {
			@bottom-right {
				content: 'Page ' counter(page) ' of ' counter(pages);
				font-size: 12px;
				margin-bottom: 25px;
			}
		}

	</style>
</head>

<body>

	<table style="height: auto; width: 768px;">
		<tbody>
			<tr>
				<td style="width: 170px;">
					<img src="./engineworks_logo/logo.png" width="160" height="122" />
				</td>
				<td style="width: 350px;">
					<p>ENGINEWORKS
						LLC<br />${companyAddress}<br />${companyPhone}<br />${companyEmail}<br />${companyWebsite}</p>
				</td>
				<td style="width: 248px; text-align: right;">
					<p><span style="font-size: 30px; font-weight: bold;">INVOICE</span><br />
						<span>No. ${invoice.number}<br /> Date: ${invoice.date}</span>
					</p>
				</td>
			</tr>
		</tbody>
	</table>

	<table style="height: auto; width: 768px; margin: 25px 0px 25px 0px;">
		<tbody>
			<tr>
				<td style="width: 450px;">
					<p><span style="font-weight: bold;">BILLED TO:</span></p>
				</td>
				<td style="width: 85px;">
					<p><span style="font-weight: bold;">VEHICLE:</span></p>
				</td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td>${invoice.customer.name}</td>
				<td>Make:</td>
				<td>${invoice.vehicle.make}</td>
			</tr>
			<tr>
				<td>${invoice.customer.address}</td>
				<td>Model:</td>
				<td>${invoice.vehicle.model}</td>
			</tr>
			<tr>
				<td>${invoice.customer.phone}</td>
				<td>Year:</td>
				<td>${invoice.vehicle.year}</td>
			</tr>
			<tr>
				<td>${invoice.customer.email}</td>
				<td>Mileage:</td>
				<td>${invoice.vehicle.mileage}</td>
			</tr>
			<tr>
				<td></td>
				<td>VIN:</td>
				<td>${invoice.vehicle.vin}</td>
			</tr>
		</tbody>
	</table>

	<#list invoice.items as i>

		<div style="width: 768px;">
		<p style="font-weight: bold; font-size: 17px;">${i.title}</p>
		</div>

		<table style="height: auto; width: 768px; border-top: 1px solid black;">
			<tr>
				<th style="width: 404px; text-align: left;">Parts</th>
				<th style="width: 90px; text-align: right;">Qty</th>
				<th style="width: 137px; text-align: right;">Price</th>
				<th style="width: 137px; text-align: right;">Total</th>
			</tr>
			<#list i.partItems as pi>
				<tr>
					<td>${pi.description}</td>
					<td style="text-align: right;">${pi.quantity}</td>
					<td style="text-align: right;">$${pi.price}</td>
					<td style="text-align: right;">$${pi.total}</td>
				</tr>
			</#list>
		</table>

		<table style="height: auto; width: 768px; border-top: 1px solid black;">
			<tr>
				<th style="width: 404px; text-align: left;">Labor</th>
				<th style="width: 90px; text-align: right;">Hrs</th>
				<th style="width: 137px; text-align: right;">Price</th>
				<th style="width: 137px; text-align: right;">Total</th>
			</tr>
			<#list i.laborItems as li>
				<tr>
					<td>${li.description}</td>
					<td style="text-align: right;">${li.hours}</td>
					<td style="text-align: right;">$${li.price}</td>
					<td style="text-align: right;">$${li.total}</td>
				</tr>
			</#list>
		</table>
		<table style="height: auto; width: 768px; border-bottom: 1px solid rgba(0,0,0,0.5);">
			<tr style="background-color: rgb(218, 218, 218);">
				<th style="width: 494px; text-align: right;">${i.title}</th>
				<th style="width: 137px; font-weight: bold; text-align: right;">SubTotal</th>
				<th style="width: 137px; font-weight: bold; text-align: right;">$${i.total}</th>
			</tr>
		</table>

	</#list>

	<div style="width: 768px; text-align: right !important; margin: 15px 0px 15px 0px; height: auto;">
		<table style="width: 387px; margin-right: 0; margin-left: auto;">
			<tr>
				<th style="text-align: right; width: 250px;">Labor</th>
				<th style="text-align: right; font-weight: normal; width: 137px;">$${laborSum}</th>
			</tr>
			<tr>
				<th style="text-align: right;">Parts</th>
				<th style="text-align: right; font-weight: normal;">$${partsSum}</th>
			</tr>
			<tr>
				<th style="text-align: right;">Taxes</th>
				<th style="text-align: right; font-weight: normal;">$${taxesSum}</th>
			</tr>
			<tr style="background-color: rgb(218, 218, 218);">
				<th style="text-align: right;">Repair Order Total</th>
				<th style="text-align: right; font-weight: normal;">$${totalSum}</th>
			</tr>
		</table>
	</div>

	<div style="width: 768px; text-align: center; margin: 50px 0px 20px 0px;">
		<p style="font-style: italic; font-size: 14px;">Thanks for your business!</p>
	</div>

</body>

</html>