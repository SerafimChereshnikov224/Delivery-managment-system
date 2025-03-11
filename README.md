## Обзор
   Тестовое задание из отбора на стажировку Серерсталь Ит-Хаб, март 2025 (задание 3).
   
   Приложение написано на Java с использованием Spring Framework. В качестве СУБД использована in-memory СУБД H2.
   
   Приложение предоставляет фукнционал для учета поставок продуктов от поставщиков.

   Приложение доступно по адресу `http://localhost:8080`. Можно использовать Postman/Insomnia для взаимодействия с REST API.
   
   При запуске приложения база данных автоматически заполнится небольшим количеством тестовых записей.

## API 

  1. `POST "http://localhost:8080/delivery"`
     Добавить новую поставку.
     
     Параметры: поставщик, дата поставки, набор продуктов (для каждого продукта необходимо указать вес).
     
     (минимальным промежуктом между поставками для конктретного поставщика выбран день)
     
     Пример запроса:
     ```
     {
        	"supplierId": 2,
        	"date": "2024-01-30",
        	"units": [
          		{
            		"productId": 1,
            		"weight": 100.5
          		},
          		{
            		"productId": 3,
            		"weight": 75.0
          		}
        	]
     }
     ```
     Ответ:
     `201 Created; new delivery added, id: 18.`
     

  2.  `GET "http://localhost:8080/delivery/report"`  

      Получить отчет по поставкам за период времени для видов продуктов по поставщикам с итогами по весу и стоимости.

      Параметры: начало и конец периода.

      **Периоды с ценой на 3 месяца на продукт от поставщика хранятся в отдельной таблице в БД.**

      **Цена на каждый поставленный продукт высчитывается автоматически по дате и поставщику.**

      Пример запроса:
     
      `http://localhost:8080/delivery/report?startDate=2024-01-01&endDate=2024-06-30`
     
     	Ответ:
     ```
     200 OK;
     {
	"deliveryReportResponses": [
		{
			"supplierName": "Supplier B",
			"products": [
				{
					"productName": "Dushes Pear",
					"totalWeight": 195.00,
					"totalPrice": 222.4500
				},
				{
					"productName": "Green Pear",
					"totalWeight": 85.00,
					"totalPrice": 107.1000
				},
				{
					"productName": "Red Apple",
					"totalWeight": 99.00,
					"totalPrice": 317.7900
				},
				{
					"productName": "Golden Apple",
					"totalWeight": 145.50,
					"totalPrice": 365.2050
				}
			],
			"supplierTotalPrice": 1012.5450
		},
		{
			"supplierName": "Supplier C",
			"products": [
				{
					"productName": "Dushes Pear",
					"totalWeight": 104.00,
					"totalPrice": 121.6800
				},
				{
					"productName": "Green Pear",
					"totalWeight": 110.00,
					"totalPrice": 143.0000
				},
				{
					"productName": "Golden Apple",
					"totalWeight": 310.00,
					"totalPrice": 781.2000
				}
			],
			"supplierTotalPrice": 1045.8800
		},
		{
			"supplierName": "Supplier A",
			"products": [
				{
					"productName": "Dushes Pear",
					"totalWeight": 850.00,
					"totalPrice": 935.0000
				},
				{
					"productName": "Green Pear",
					"totalWeight": 130.00,
					"totalPrice": 166.4000
				},
				{
					"productName": "Golden Apple",
					"totalWeight": 1081.00,
					"totalPrice": 2702.5000
				},
				{
					"productName": "Red Apple",
					"totalWeight": 317.00,
					"totalPrice": 973.4000
				}
			],
			"supplierTotalPrice": 4777.3000
		}
	],
	"deliveryTotalPrice": 6835.7250
    }
    ```

3. `POST http://localhost:8080/prices/{supplierId}/quarter-price`
   
   Добавить цену на продукт от поставщика на 3 следующих месяца. Параметры: поставщик, новая цена.
   
   Пример запроса:
   ```
   {
	    "supplierId" : 3,
	    "newPrice" : 3.50
   }
   ```
   Ответ:
   `201 Created; quarter price added for product: Red Apple`

4. `GET http://localhost:8080/prices/{productId}`
   
   Получить цену на продукт от всех поставщиков на все сроки. Параметры: продукт.
   
   Пример запроса:
   `http://localhost:8080/prices/3`
   
   Ответ:
   ```
   200 OK;
   [
	{
		"supplierName": "Supplier A",
		"startDate": "2024-01-01",
		"endDate": "2024-03-31",
		"price": 1.10
	},
	{
		"supplierName": "Supplier A",
		"startDate": "2024-04-01",
		"endDate": "2024-06-30",
		"price": 1.15
	},
	{
		"supplierName": "Supplier B",
		"startDate": "2024-01-01",
		"endDate": "2024-03-31",
		"price": 1.11
	},
	{
		"supplierName": "Supplier B",
		"startDate": "2024-04-01",
		"endDate": "2024-06-30",
		"price": 1.16
	},
	{
		"supplierName": "Supplier C",
		"startDate": "2024-01-01",
		"endDate": "2024-03-31",
		"price": 1.12
	},
	{
		"supplierName": "Supplier C",
		"startDate": "2024-04-01",
		"endDate": "2024-06-30",
		"price": 1.17
	}
   ]
```
