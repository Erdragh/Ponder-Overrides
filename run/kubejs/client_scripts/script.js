// priority: 0
console.log("test")
onEvent("ponder.override", event => {
    console.log("overriding event ponder")
    const item = event.getItem();
    console.log(item);
    event.override("minecraft:apple");
})
