(ns gilded-rose.core
  (:gen-class))

(defn update-quality [items]
  (map
    (fn[item] (cond
                (and (< (:sell-in item) 0) (= "Backstage passes to a TAFKAL80ETC concert" (:name item)))
                (merge item {:quality 0})
                (or (= (:name item) "Aged Brie") (= (:name item) "Backstage passes to a TAFKAL80ETC concert"))
                (if (and (= (:name item) "Backstage passes to a TAFKAL80ETC concert") (>= (:sell-in item) 5) (< (:sell-in item) 10) (< (:quality item) 50))
                  (if (<= (:quality item) 48)
                    (merge item {:quality (inc (inc (:quality item)))})
                    (merge item {:quality (inc (:quality item))}))
                  (if (and (= (:name item) "Backstage passes to a TAFKAL80ETC concert") (>= (:sell-in item) 0) (< (:sell-in item) 5) (< (:quality item) 50))
                    (if (<= (:quality item) 47)
                      (merge item {:quality (inc (inc (inc (:quality item))))})
                        (if (<= (:quality item) 48)
                          (merge item {:quality (inc (inc (:quality item)))})
                          (merge item {:quality (inc (:quality item))})))
                    (if (and (= (:name item "Aged Brie")) (< (:sell-in item) 0) (< (:quality item) 50))
                      (if (<= (:quality item) 48)
                        (merge item {:quality (inc (inc (:quality item)))})
                        (merge item {:quality (inc (:quality item))}))
                      (if (< (:quality item) 50)
                        (merge item {:quality (inc (:quality item))})
                        item))))
                (< (:sell-in item) 0)
                (if (= "Backstage passes to a TAFKAL80ETC concert" (:name item))
                  (merge item {:quality 0})
                  (if (and (> (:quality item) 0) (or (= "+5 Dexterity Vest" (:name item)) (= "Elixir of the Mongoose" (:name item))))
                    (if (> (:quality item) 1)
                      (merge item {:quality (- (:quality item) 2)})
                      (merge item {:quality 0}))
                    item))
                (and (> (:quality item) 0) (or (= "+5 Dexterity Vest" (:name item)) (= "Elixir of the Mongoose" (:name item))))
                (merge item {:quality (dec (:quality item))})
                :else item))
    (map (fn [item]
           (if (not= "Sulfuras, Hand of Ragnaros" (:name item))
             (merge item {:sell-in (dec (:sell-in item))})
             item))
         items)))

(defn item [item-name, sell-in, quality]
  {:name item-name, :sell-in sell-in, :quality quality})

(defn update-current-inventory[]
  (let [inventory
        [
         (item "+5 Dexterity Vest" 10 20)
         (item "Aged Brie" 2 0)
         (item "Elixir of the Mongoose" 5 7)
         (item "Sulfuras, Hand of Ragnaros" 0 80)
         (item "Backstage passes to a TAFKAL80ETC concert" 15 20)
         ]]
    (update-quality inventory)))
