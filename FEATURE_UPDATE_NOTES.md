Add a new table in the warehouse database named customer_account.
This table will store account information when a supplier or customer is created.
Columns:

account_no
orga
loca
account_title
is_closed
close_reason
next
comment

Create a new table payment_visit_history (for visit details and comments).

On bulk post requests, add the following fields:

comment
next_visit
value_date (provided from an external source)
trans_type_business_day

Add a new column in the transaction_type table:
requires_authorization (boolean: true/false)
Set it to true or false according to the transaction type.

Add new columns in trasaction main table
authorization_date_time
canceled_by
reversed_by