# Ponder Overrides
KubeJS extension that allows overriding which ponder gets loaded based on an Event.

Example usage:

`client_scripts/ponder.js`
```js
onEvent("ponder.override", event => {
    // Access the item and determine the ponder you want to show
    const item = event.getItem();
    // Access the tag of the ponder (This is optional and only present when opened via the Ponder UI)
    const tag = event.getTag();
    // Override the ponder id to show a different ponder. Not calling this results in the default ponder
    event.override("create:super_glue");
})
```
