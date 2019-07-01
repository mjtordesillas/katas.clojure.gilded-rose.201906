(ns gilded-rose.core
  (:gen-class))

(defn- update-item-quality-by [item delta]
  (merge item {:quality (+ (:quality item) delta)}))

(defn- age-one-day [item]
  (merge item {:sell-in (dec (:sell-in item))}))

(defn update-quality [items]
  (map
    (fn[item] (cond
                (= (:name item) "Backstage passes to a TAFKAL80ETC concert")
                (if (< (:sell-in item) 0)
                  (merge item {:quality 0})
                  (if (and (>= (:sell-in item) 5) (< (:sell-in item) 10) (< (:quality item) 50))
                  (if (<= (:quality item) 48)
                    (update-item-quality-by item 2)
                    (update-item-quality-by item 1))
                  (if (and (>= (:sell-in item) 0) (< (:sell-in item) 5) (< (:quality item) 50))
                    (if (<= (:quality item) 47)
                      (update-item-quality-by item 3)
                        (if (<= (:quality item) 48)
                          (update-item-quality-by item 2)
                          (update-item-quality-by item 1)))
                    (if (< (:quality item) 50)
                      (merge item {:quality (inc (:quality item))})
                      item))))
                (= (:name item) "Aged Brie")
                (if (and (< (:sell-in item) 0) (< (:quality item) 50))
                  (if (<= (:quality item) 48)
                    (update-item-quality-by item 2)
                    (update-item-quality-by item 1))
                  (if (< (:quality item) 50)
                    (update-item-quality-by item 1)
                    item))
                (and (or (= "+5 Dexterity Vest" (:name item)) (= "Elixir of the Mongoose" (:name item))))
                (if (< (:sell-in item) 0)
                  (if (> (:quality item) 1)
                    (update-item-quality-by item -2)
                    (merge item {:quality 0}))
                  (if (> (:quality item) 0)
                    (update-item-quality-by item -1)
                    item))
                :else (merge item {:quality 80})))
    (map (fn [item]
           (if (not= "Sulfuras, Hand of Ragnaros" (:name item))
             (age-one-day item)
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
