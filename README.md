# Compose UI Challenge Lab

Build Jetpack Compose screens one small challenge at a time.

The data layer is already wired: models, mock data, repositories, themes, assets, and app setup are ready. Your job is the UI. Vibe coding can make a screen appear quickly, but it cannot make you understand what you built. Try each challenge once yourself—the extra typing is where Compose starts to make sense.

There are two branches:

- `exercise` — the UI starting point
- `solution` — the completed reference implementation

## Level 1 — Foundation

Four focused screens, in a deliberately sensible order:

<table>
<tr>
<td width="42%" valign="top" align="center">
<img src="docs/contact.png" alt="Contact List demo" width="100%">
</td>
<td width="58%" valign="top">
<h3>1. Contact List</h3>
<p>Build a clean, scrollable contact directory with reusable rows, stable keys, and favorite actions.</p>
<strong>What you’ll practice</strong>
<ul>
<li><code>LazyColumn</code> and stable item keys</li>
<li>Reusable row composables and list spacing</li>
<li>State-driven favorite actions</li>
</ul>
</td>
</tr>
<tr>
<td width="42%" valign="top" align="center">
<a href="docs/market.mp4"><img src="docs/market.gif" alt="Compose Market demo" width="100%"></a>
</td>
<td width="58%" valign="top">
<h3>2. Compose Market</h3>
<p>Compose a colorful product grid with real product imagery, ratings, discounts, prices, and favorite states.</p>
<strong>What you’ll practice</strong>
<ul>
<li><code>LazyVerticalGrid</code> and card composition</li>
<li>Image cropping, badges, and overlays</li>
<li>Ratings, discounts, prices, and favorites</li>
</ul>
</td>
</tr>
<tr>
<td width="42%" valign="top" align="center">
<a href="docs/recipe.mp4"><img src="docs/recipe.gif" alt="Recipe demo" width="100%"></a>
</td>
<td width="58%" valign="top">
<h3>3. Recipe</h3>
<p>Build a recipe search screen with category chips, derived filtering, rich cards, remote images, and favorite actions.</p>
<strong>What you’ll practice</strong>
<ul>
<li><code>LazyRow</code> and <code>FilterChip</code></li>
<li>Search and category filtering with <code>Flow</code>/<code>StateFlow</code></li>
<li>Remote images and rich list cards</li>
</ul>
</td>
</tr>
<tr>
<td width="42%" valign="top" align="center">
<a href="docs/settings.mp4"><img src="docs/settings.gif" alt="Settings demo" width="100%"></a>
</td>
<td width="58%" valign="top">
<h3>4. Settings</h3>
<p>Organize switches, checkboxes, radio choices, theme selection, and text-size controls into a polished settings screen.</p>
<strong>What you’ll practice</strong>
<ul>
<li>Switches, checkboxes, radio buttons, and sliders</li>
<li>Grouped sections and reusable setting rows</li>
<li>State hoisting and event callbacks</li>
</ul>
</td>
</tr>
</table>

## A small rule

Pick a project, build the screen, and peek at `solution` when you get stuck. Let AI help with the boring parts, then read the result and make it yours. Compose becomes much less intimidating after you have typed it with your own keyboard.
