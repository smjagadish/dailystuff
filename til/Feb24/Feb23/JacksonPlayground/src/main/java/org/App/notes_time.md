These are rough notes on Time handling in request/response.

> Request

Let us assume the following ways in which a client can send time info

* send in Zulu time . eg: 2020-12-12T13:12:00Z ( after 00 we can have.xx to represent millisec)
* send as local time with info on the tz. eg: 2020-12-12T08:12:00-05:00
* send as local time without info on the tz. eg: 2020-12-12T08:12:00 ( note that this requires an implicit understanding of the underlying TZ)
* send as agreed-time , i.e UTC or local , in a custom format. eg: 2020-12-12 08:12:00 { local} or 2020-12-12 13:12:00 { utc }

when the controller recieves the above listed time data , here are the ways in which we can store them

* data sent as zulu time can be mapped to Instant type and then converted to any other type. Conversion may require a custom de-serializer
* data sent as local time with info on tz can be mapped to OffsetDateTime type and then converted to other type. Conversion may require a custom de-serializer
* data sent as local time without info on TZ can be mapped to LocalDateTime type and then converted to other type. Conversion may require a custom de-serializer
* data sent as agreed-time in custom format almost always needs a custom de-serializer. You map it to localdatetime type and then convert to other types

> Response
