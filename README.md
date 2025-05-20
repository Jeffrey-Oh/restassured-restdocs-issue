## Duplicate `Content-Type` Header Issue

The issue was caused by the duplicate addition of the `Content-Type` header due to the call to `doAddHeaderValue` in the `else` clause of the `MockHttpServletRequest#addHeader(...)` method.  
This behavior is scheduled to be fixed in **Spring Framework 6.2.8**.

> 📌 This was **not** an issue caused by the combination of **RestAssured** and **Spring REST Docs**.

In the actual execution, it is presumed that the same spec definition was added more than once, resulting in duplicate headers being passed to Spring Test.

The modified code resolving this issue is available in the `refactor` branch.

### References

- [Spring GitHub Issue #34913 (Comment)](https://github.com/spring-projects/spring-framework/issues/34913)  
- [Stack Overflow: Duplicate Content-Type Header in Spring REST Docs using RestAssuredMockMvc](https://stackoverflow.com/questions/79616625/duplicate-content-type-header-in-spring-rest-docs-using-restassuredmockmvc)
