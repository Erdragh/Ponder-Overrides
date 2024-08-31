// priority: 0
onEvent("ponder.override", event => {
    console.log("overriding event ponder")
    const item = event.getItem();
    console.log(item);
    //event.override("create:super_glue");
})
